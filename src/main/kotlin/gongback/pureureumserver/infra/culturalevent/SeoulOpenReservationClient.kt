package gongback.pureureumserver.infra.culturalevent

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange

interface SeoulOpenReservationClient {
    @PostExchange("/web/reservation/selectListRecentSvcAjax.do")
    fun getSeongdongCulturalEvent(
        @RequestParam("recent_svc_id") recentSvcId: String,
        @RequestParam("locale") locale: String = "ko",
        @RequestParam("now_svc_id") nowSvcId: String,
    ): SeongdongCultureEventInfoResponse

    @PostExchange("{filePath}")
    fun getSeongdongCulturalThumbnail(
        @PathVariable("filePath") filePath: String,
    ): ByteArray
}
