package gongback.pureureumserver.controller

import gongback.pureureumserver.domain.culturalevent.CulturalEventSortType
import gongback.pureureumserver.service.dto.CulturalEventDto
import gongback.pureureumserver.service.dto.CulturalEventResponse
import gongback.pureureumserver.service.dto.SliceResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@Tag(name = "CulturalEvent", description = "문화 행사 API")
interface CulturalEventController {
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "문화 행사 목록 조회 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun getCulturalEvents(
        @Parameter(
            description = "조회할 문화 행사의 개수",
            example = "10",
            required = false,
        )
        size: Int,
        @Parameter(
            description = "마지막 문화 행사의 id",
            example = "1",
            required = false,
        )
        lastId: Long?,
        @Parameter(
            description = "마지막 문화 행사의 서비스 시작 시간",
            example = "2021-08-01T00:00:00",
            required = false,
        )
        lastDateTime: LocalDateTime?,
        @Parameter(
            description = "정렬 기준",
            example = "SERVICE_LATEST(서비스 개시 최신순), SERVICE_OLDEST(서비스 개시 오래된순), " +
                "REGISTER_LATEST(접수 개시 최신순), REGISTER_OLDEST(접수 개시 오래된순)",
            required = false,
        )
        sortType: CulturalEventSortType,
    ): ResponseEntity<SliceResponse<CulturalEventDto>>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "내가 참여한 문화 행사 목록 조회 성공",
        ),
    )
    fun getMyAttendedCulturalEvents(): ResponseEntity<CulturalEventResponse>
}
