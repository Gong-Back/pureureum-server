package gongback.pureureumserver.infra.culturalevent

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange

interface SeoulOpenDataClient {
    @GetExchange("/SDListPublicReservationCulture/{start-index}/{end-index}")
    fun getSeongdongCulturalEvents(
        @PathVariable("start-index") startIndex: Long,
        @PathVariable("end-index") endIndex: Long,
    ): SeongdongCulturalEventResponse
}
