package gongback.pureureumserver.service

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.CulturalEventSortType
import gongback.pureureumserver.domain.culturalevent.getMyAttendedCulturalEvents
import gongback.pureureumserver.service.dto.CulturalEventDto
import gongback.pureureumserver.service.dto.CulturalEventResponse
import gongback.pureureumserver.service.dto.CulturalEventSliceResponse
import gongback.pureureumserver.service.dto.SliceResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CulturalEventService(
    private val culturalEventRepository: CulturalEventRepository,
) {
    fun getCulturalEvents(
        size: Int,
        lastId: Long?,
        lastDateTime: LocalDateTime?,
        sortType: CulturalEventSortType,
    ): SliceResponse<CulturalEventDto> {
        require((lastId == null && lastDateTime == null) || (lastId != null && lastDateTime != null)) {
            "lastId($lastId)와 lastDateTime($lastDateTime)은 둘 다 null이거나 둘 다 null이 아니어야 합니다."
        }
        val culturalEventSlice = culturalEventRepository.findSliceBy(size, lastId, lastDateTime, sortType)
        return getCulturalEventSliceResponse(culturalEventSlice, size, sortType)
    }

    fun getMyAttendedCulturalEvents(): CulturalEventResponse {
        // TODO: 추후 나의 문화 행사 목록 조회 기능 구현
        return CulturalEventResponse(culturalEventRepository.getMyAttendedCulturalEvents())
    }

    private fun getCulturalEventSliceResponse(
        culturalEventSlice: List<CulturalEvent>,
        size: Int,
        sortType: CulturalEventSortType,
    ): CulturalEventSliceResponse {
        val hasNext = culturalEventSlice.size > size
        val content = if (hasNext) culturalEventSlice.dropLast(1) else culturalEventSlice
        return createCulturalEventSliceResponse(content, sortType, hasNext)
    }

    private fun createCulturalEventSliceResponse(
        content: List<CulturalEvent>,
        sortType: CulturalEventSortType,
        hasNext: Boolean,
    ): CulturalEventSliceResponse {
        if (content.isNotEmpty()) {
            val lastContent = content.last()
            val lastDateTime = culturalEventDateTimeToStringBySortType(sortType, lastContent)
            val lastId = lastContent.id
            return CulturalEventSliceResponse(
                lastId,
                lastDateTime,
                hasNext,
                content.map { CulturalEventDto(it) },
            )
        }

        return CulturalEventSliceResponse(
            hasNext = hasNext,
            content = content.map { CulturalEventDto(it) },
        )
    }

    private fun culturalEventDateTimeToStringBySortType(
        sortType: CulturalEventSortType,
        culturalEvent: CulturalEvent,
    ): LocalDateTime = when (sortType) {
        CulturalEventSortType.SERVICE_LATEST -> culturalEvent.serviceStartDateTime
        CulturalEventSortType.SERVICE_OLDEST -> culturalEvent.serviceStartDateTime
        CulturalEventSortType.REGISTER_LATEST -> culturalEvent.registerStartDateTime
        CulturalEventSortType.REGISTER_OLDEST -> culturalEvent.registerStartDateTime
    }
}
