package gongback.pureureumserver.service

import gongback.pureureumserver.service.dto.CulturalEventResponse

interface CulturalEventClient {
    fun getCulturalEvents(startIndex: Long, endIndex: Long): CulturalEventResponse
}
