package gongback.pureureumserver.infra.culturalevent

import gongback.pureureumserver.service.CulturalEventClient
import gongback.pureureumserver.service.dto.CulturalEventDto
import gongback.pureureumserver.service.dto.CulturalEventResponse
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val SEONGDONG_DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S"

@Component
class CulturalEventClientAdapter(
    private val seoulOpenDataClient: SeoulOpenDataClient,
) : CulturalEventClient {
    override fun getCulturalEvents(startIndex: Long, endIndex: Long): CulturalEventResponse {
        val seongdongCulturalEventResponse = runCatching { seoulOpenDataClient.getSeongdongCulturalEvents(startIndex, endIndex) }
            .onFailure { throw RuntimeException("${it.message}", it) }
            .getOrThrow()
        return toCulturalEventResponse(seongdongCulturalEventResponse)
    }

    private fun toCulturalEventResponse(seongdongCulturalEventResponse: SeongdongCulturalEventResponse): CulturalEventResponse {
        val culturalEventDtos = seongdongCulturalEventResponse.seoulOpenCulturalEventDtos
            .filter {
                it.serviceStartDateTime != null && it.serviceEndDateTime != null && it.registerStartDateTime != null && it.registerEndDateTime != null
            }
            .map {
                CulturalEventDto(
                    culturalEventId = it.serviceId,
                    clasName = it.minClassName,
                    region = it.areaName,
                    state = it.serviceState,
                    paymentMethod = it.paymentMethod,
                    placeName = it.placeName,
                    target = it.userInfo,
                    content = it.serviceName,
                    serviceUrl = it.serviceUrl,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    serviceStartDateTime = stringDateTimeToLocalDateTime(it.serviceStartDateTime!!),
                    serviceEndDateTime = stringDateTimeToLocalDateTime(it.serviceEndDateTime!!),
                    registerStartDateTime = stringDateTimeToLocalDateTime(it.registerStartDateTime!!),
                    registerEndDateTime = stringDateTimeToLocalDateTime(it.registerEndDateTime!!),
                )
            }
        return CulturalEventResponse(
            listTotalCount = seongdongCulturalEventResponse.totalCount,
            culturalEventDtos = culturalEventDtos,
        )
    }

    /**
     * 성동구 문화 행사 API에서 받은 날짜를 LocalDateTime으로 변환한다.
     */
    private fun stringDateTimeToLocalDateTime(dateTime: String): LocalDateTime {
        return dateTime.let {
            LocalDateTime.parse(
                it,
                DateTimeFormatter.ofPattern(SEONGDONG_DEFAULT_DATE_FORMAT),
            )
        }
    }
}
