package gongback.pureureumserver.domain.suggestion

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

fun SuggestionRepository.getSuggestionById(suggestionId: Long): Suggestion = findByIdOrNull(suggestionId)
    ?: throw NoSuchElementException("해당 제안($suggestionId)이 존재하지 않습니다.")

@Repository
interface SuggestionRepository : JpaRepository<Suggestion, Long>, CustomSuggestionRepository {
    @Query("select s from Suggestion s where s.information.endDate <= :now")
    fun findByEndDateBeforeThanEqual(@Param("now") now: LocalDate): List<Suggestion>
}

interface CustomSuggestionRepository {
    fun findSliceBy(
        size: Int,
        lastId: Long?,
        sortType: SuggestionSortType,
        status: SuggestionStatus,
    ): List<Suggestion>
}

class SuggestionRepositoryImpl(
    private val queryFactory: QueryFactory,
) : CustomSuggestionRepository {
    override fun findSliceBy(
        size: Int,
        lastId: Long?,
        sortType: SuggestionSortType,
        status: SuggestionStatus,
    ): List<Suggestion> = queryFactory.listQuery {
        select(entity(Suggestion::class))
        from(entity(Suggestion::class))
        associate(
            entity(Suggestion::class),
            entity(SuggestionInformation::class),
            on(Suggestion::information),
        )
        where(
            and(
                col(SuggestionInformation::status).equal(status),
                dynamicPredicateBySuggestionSortType(lastId, sortType),
            ),
        )
        orderBy(dynamicOrderingBySuggestionSortType(sortType))
        limit(size + 1)
    }

    private fun <T> CriteriaQueryDsl<T>.dynamicPredicateBySuggestionSortType(
        lastId: Long?,
        sortType: SuggestionSortType,
    ): PredicateSpec {
        return if (lastId != null) {
            when (sortType) {
                SuggestionSortType.LATEST -> {
                    col(Suggestion::id).lessThan(lastId)
                }
            }
        } else {
            PredicateSpec.empty
        }
    }

    private fun <T> CriteriaQueryDsl<T>.dynamicOrderingBySuggestionSortType(
        sortType: SuggestionSortType,
    ): List<OrderSpec> =
        when (sortType) {
            SuggestionSortType.LATEST -> listOf(
                col(Suggestion::id).desc(),
            )
        }
}

enum class SuggestionSortType(private val description: String) {
    LATEST("최신순"),
}
