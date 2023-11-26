package gongback.pureureumserver.service

import gongback.pureureumserver.infra.culturalevent.SeongdongCultureEventInfoResponse
import gongback.pureureumserver.service.dto.CulturalEventResponse

interface CulturalEventClient {
    fun getCulturalEvents(startIndex: Long, endIndex: Long): CulturalEventResponse
    fun getCulturalEvent(serviceId: String): SeongdongCultureEventInfoResponse.SeoulOpenCulturalEventDto?
    fun getCulturalEventThumbnail(response: SeongdongCultureEventInfoResponse.SeoulOpenCulturalEventDto): ByteArray
}
