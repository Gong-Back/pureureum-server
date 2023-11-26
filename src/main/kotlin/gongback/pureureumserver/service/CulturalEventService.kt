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
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CulturalEventService(
    private val culturalEventRepository: CulturalEventRepository,
    private val fileClient: FileClient,
) {

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun getMyAttendedCulturalEvents(): CulturalEventResponse {
        val culturalEvents = culturalEventRepository.getMyAttendedCulturalEvents()
        return CulturalEventResponse(
            culturalEvents = culturalEvents.map {
                val imageUrl = fileClient.getImageUrl(it.thumbnail.fileKey)
                CulturalEventDto(it, imageUrl)
            },
        )
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
                lastId = lastId,
                lastDateTime = lastDateTime,
                hasNext = hasNext,
                content = content.map {
                    val imageUrl = fileClient.getImageUrl(it.thumbnail.fileKey)
                    CulturalEventDto(it, imageUrl)
                },
            )
        }

        return CulturalEventSliceResponse(
            hasNext = hasNext,
            content = content.map {
                val imageUrl = fileClient.getImageUrl(it.thumbnail.fileKey)
                CulturalEventDto(it, imageUrl)
            },
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
