package gongback.pureureumserver.service.dto

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventCoordinates
import gongback.pureureumserver.domain.culturalevent.CulturalEventInformation
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "문화 행사 목록 응답")
data class CulturalEventResponse(
    @Schema(description = "문화 행사 목록 개수")
    val listTotalCount: Long,
    @Schema(description = "문화 행사 목록")
    val culturalEventDtos: List<CulturalEventDto>,
) {
    constructor(culturalEvents: List<CulturalEvent>) : this(
        listTotalCount = culturalEvents.size.toLong(),
        culturalEventDtos = culturalEvents.map { CulturalEventDto(it) },
    )
}

@Schema(description = "문화 행사 응답")
data class CulturalEventDto(
    @Schema(description = "문화 행사 아이디")
    val id: Long? = null,
    @Schema(description = "공공데이터포털 문화행사 Id")
    val culturalEventId: String,
    @Schema(description = "분류")
    val clasName: String?,
    @Schema(description = "지역")
    val region: String?,
    @Schema(description = "문화행사 상태")
    val state: String?,
    @Schema(description = "내용")
    val content: String?,
    @Schema(description = "지불 방법")
    val paymentMethod: String?,
    @Schema(description = "문화행사 장소명")
    val placeName: String?,
    @Schema(description = "문화행사 대상")
    val target: String?,
    @Schema(description = "문화행사 바로가기")
    val serviceUrl: String?,
    @Schema(description = "문화행사 위도")
    val latitude: String?,
    @Schema(description = "문화행사 경도")
    val longitude: String?,
    @Schema(description = "문화행사 서비스 시작 시간")
    val serviceStartDateTime: LocalDateTime,
    @Schema(description = "문화행사 서비스 종료 시간")
    val serviceEndDateTime: LocalDateTime,
    @Schema(description = "문화행사 접수 시작 시간")
    val registerStartDateTime: LocalDateTime,
    @Schema(description = "문화행사 접수 종료 시간")
    val registerEndDateTime: LocalDateTime,
) {
    constructor(culturalEvent: CulturalEvent) : this(
        id = culturalEvent.id,
        culturalEventId = culturalEvent.culturalEventId,
        clasName = culturalEvent.className,
        region = culturalEvent.region,
        state = culturalEvent.state,
        content = culturalEvent.content,
        paymentMethod = culturalEvent.paymentMethod,
        placeName = culturalEvent.placeName,
        target = culturalEvent.target,
        serviceUrl = culturalEvent.serviceUrl,
        latitude = culturalEvent.coordinates?.latitude,
        longitude = culturalEvent.coordinates?.longitude,
        serviceStartDateTime = culturalEvent.serviceStartDateTime,
        serviceEndDateTime = culturalEvent.serviceEndDateTime,
        registerStartDateTime = culturalEvent.registerStartDateTime,
        registerEndDateTime = culturalEvent.registerEndDateTime,
    )

    fun toCulturalEventInformation() = CulturalEventInformation(
        culturalEventId = culturalEventId,
        className = clasName ?: "",
        region = region ?: "",
        state = state ?: "",
        content = content ?: "",
        paymentMethod = paymentMethod ?: "",
        placeName = placeName ?: "",
        target = target ?: "",
        serviceUrl = serviceUrl ?: "",
        coordinates = CulturalEventCoordinates(
            latitude = latitude ?: "",
            longitude = longitude ?: "",
        ),
        serviceStartDateTime = serviceStartDateTime,
        serviceEndDateTime = serviceEndDateTime,
        registerStartDateTime = registerStartDateTime,
        registerEndDateTime = registerEndDateTime,
    )
}

@Schema(description = "문화 행사 슬라이스 응답")
open class CulturalEventSliceResponse(
    @Schema(description = "마지막 문화 행사의 id")
    val lastId: Long? = null,
    @Schema(description = "마지막 문화 행사 시간")
    val lastDateTime: LocalDateTime? = null,
    @Schema(description = "다음 페이지가 있는지 여부")
    override val hasNext: Boolean,
    @Schema(description = "문화 행사 목록")
    override val content: List<CulturalEventDto>,
) : SliceResponse<CulturalEventDto>(hasNext, content) {
    constructor(lastId: Long, lastDateTime: LocalDateTime, size: Int, content: List<CulturalEventDto>) : this(
        lastId,
        lastDateTime,
        content.size > size,
        if (content.size > size) content.dropLast(1) else content,
    )
}
