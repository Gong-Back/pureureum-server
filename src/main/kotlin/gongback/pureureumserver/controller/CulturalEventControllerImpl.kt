package gongback.pureureumserver.controller

import gongback.pureureumserver.domain.culturalevent.CulturalEventSortType
import gongback.pureureumserver.service.CulturalEventService
import gongback.pureureumserver.service.dto.CulturalEventDto
import gongback.pureureumserver.service.dto.CulturalEventResponse
import gongback.pureureumserver.service.dto.SliceResponse
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/cultural-events")
class CulturalEventControllerImpl(
    private val culturalEventService: CulturalEventService,
) : CulturalEventController {
    @GetMapping
    override fun getCulturalEvents(
        @Positive(message = "사이즈 값은 양수여야 합니다.")
        @RequestParam("size", defaultValue = "10", required = false)
        size: Int,
        @RequestParam("lastId", required = false)
        lastId: Long?,
        @RequestParam("lastDateTime", required = false)
        lastDateTime: LocalDateTime?,
        @RequestParam("sortType", defaultValue = "SERVICE_LATEST", required = false)
        sortType: CulturalEventSortType,
    ): ResponseEntity<SliceResponse<CulturalEventDto>> {
        val response = culturalEventService.getCulturalEvents(size, lastId, lastDateTime, sortType)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/my-attended")
    override fun getMyAttendedCulturalEvents(): ResponseEntity<CulturalEventResponse> {
        val response = culturalEventService.getMyAttendedCulturalEvents()
        return ResponseEntity.ok(response)
    }

}
