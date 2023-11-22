package gongback.pureureumserver.domain.culturalevent

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

fun CulturalEventRepository.existsByCulturalEventId(culturalEventId: String): Boolean =
    existsByInformationCulturalEventId(culturalEventId)

fun CulturalEventRepository.getMyAttendedCulturalEvents(): List<CulturalEvent> =
    findTop5OByOrderByInformationServiceStartDateTimeDesc()

@Repository
interface CulturalEventRepository : JpaRepository<CulturalEvent, Long>, CustomCulturalEventRepository {
    fun existsByInformationCulturalEventId(culturalEventId: String): Boolean

    fun findTop5OByOrderByInformationServiceStartDateTimeDesc(): List<CulturalEvent>
}

interface CustomCulturalEventRepository {
    fun findSliceBy(
        size: Int,
        lastId: Long?,
        lastDateTime: LocalDateTime?,
        sortType: CulturalEventSortType,
    ): List<CulturalEvent>
}

class CulturalEventRepositoryImpl(private val queryFactory: QueryFactory) : CustomCulturalEventRepository {
    override fun findSliceBy(size: Int, lastId: Long?, lastDateTime: LocalDateTime?, sortType: CulturalEventSortType): List<CulturalEvent> {
        return queryFactory.listQuery {
            select(entity(CulturalEvent::class))
            from(entity(CulturalEvent::class))
            associate(
                entity(CulturalEvent::class),
                entity(CulturalEventInformation::class),
                on(CulturalEvent::information),
            )
            where(
                dynamicPredicateByCulturalEventSortType(lastId, lastDateTime, sortType),
            )
            orderBy(dynamicOrderingByCulturalEventSortType(sortType))
            limit(size + 1)
        }
    }

    private fun <T> CriteriaQueryDsl<T>.dynamicPredicateByCulturalEventSortType(
        lastId: Long?,
        lastDateTime: LocalDateTime?,
        sortType: CulturalEventSortType,
    ): PredicateSpec {
        return if (lastId != null && lastDateTime != null) {
            when (sortType) {
                CulturalEventSortType.SERVICE_LATEST -> {
                    or(
                        col(CulturalEventInformation::serviceStartDateTime).lessThan(lastDateTime),
                        and(
                            col(CulturalEventInformation::serviceStartDateTime).equal(lastDateTime),
                            col(CulturalEvent::id).lessThan(lastId),
                        ),
                    )
                }

                CulturalEventSortType.SERVICE_OLDEST -> {
                    or(
                        col(CulturalEventInformation::serviceStartDateTime).greaterThan(lastDateTime),
                        and(
                            col(CulturalEventInformation::serviceStartDateTime).equal(lastDateTime),
                            col(CulturalEvent::id).lessThan(lastId),
                        ),
                    )
                }

                CulturalEventSortType.REGISTER_LATEST -> {
                    or(
                        col(CulturalEventInformation::registerStartDateTime).lessThan(lastDateTime),
                        and(
                            col(CulturalEventInformation::registerStartDateTime).equal(lastDateTime),
                            col(CulturalEvent::id).lessThan(lastId),
                        ),
                    )
                }

                CulturalEventSortType.REGISTER_OLDEST -> {
                    or(
                        col(CulturalEventInformation::registerStartDateTime).greaterThan(lastDateTime),
                        and(
                            col(CulturalEventInformation::registerStartDateTime).equal(lastDateTime),
                            col(CulturalEvent::id).lessThan(lastId),
                        ),
                    )
                }
            }
        } else {
            PredicateSpec.empty
        }
    }

    private fun <T> CriteriaQueryDsl<T>.dynamicOrderingByCulturalEventSortType(
        sortType: CulturalEventSortType,
    ): List<OrderSpec> =
        when (sortType) {
            CulturalEventSortType.SERVICE_LATEST -> listOf(
                col(CulturalEventInformation::serviceStartDateTime).desc(),
                col(CulturalEvent::id).desc(),
            )

            CulturalEventSortType.SERVICE_OLDEST -> listOf(
                col(CulturalEventInformation::serviceStartDateTime).asc(),
                col(CulturalEvent::id).desc(),
            )

            CulturalEventSortType.REGISTER_LATEST -> listOf(
                col(CulturalEventInformation::registerStartDateTime).desc(),
                col(CulturalEvent::id).desc(),
            )

            CulturalEventSortType.REGISTER_OLDEST -> listOf(
                col(CulturalEventInformation::registerStartDateTime).asc(),
                col(CulturalEvent::id).desc(),
            )
        }
}

enum class CulturalEventSortType(private val description: String) {
    SERVICE_LATEST("서비스 개시 최신순"),
    SERVICE_OLDEST("서비스 개시 오래된순"),
    REGISTER_LATEST("접수 개시 최신순"),
    REGISTER_OLDEST("접수 개시 오래된순"),
}
