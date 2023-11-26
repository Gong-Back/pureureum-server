package gongback.pureureumserver.infra.culturalevent

import com.fasterxml.jackson.annotation.JsonProperty

data class SeongdongCulturalEventResponse(
    @JsonProperty("SDListPublicReservationCulture")
    val seoulOpenCulturalEventResult: SeoulOpenCulturalEventResult?,
) {
    val totalCount = seoulOpenCulturalEventResult?.listTotalCount ?: 0
    val seoulOpenCulturalEventDtos = seoulOpenCulturalEventResult?.row ?: emptyList()

    data class SeoulOpenCulturalEventResult(
        @JsonProperty("list_total_count")
        val listTotalCount: Long,
        @JsonProperty("RESULT")
        val resultStatus: ResultStatus,
        val row: List<SeoulOpenCulturalEventDto>?,
    ) {
        data class ResultStatus(
            @JsonProperty("CODE")
            val code: String,
            @JsonProperty("MESSAGE")
            val message: String,
        )

        data class SeoulOpenCulturalEventDto(
            @JsonProperty("SVCID")
            val serviceId: String,
            @JsonProperty("MINCLASSNM")
            val minClassName: String?,
            @JsonProperty("AREANM")
            val areaName: String?,
            @JsonProperty("SVCSTATNM")
            val serviceState: String?,
            @JsonProperty("SVCNM")
            val serviceName: String?,
            @JsonProperty("PAYATNM")
            val paymentMethod: String?,
            @JsonProperty("PLACENM")
            val placeName: String?,
            @JsonProperty("USETGTINFO")
            val userInfo: String?,
            @JsonProperty("SVCURL")
            val serviceUrl: String?,
            @JsonProperty("X")
            val latitude: String?,
            @JsonProperty("Y")
            val longitude: String?,
            @JsonProperty("SVCOPNBGNDT")
            val serviceStartDateTime: String?,
            @JsonProperty("SVCOPNENDDT")
            val serviceEndDateTime: String?,
            @JsonProperty("RCPTBGNDT")
            val registerStartDateTime: String?,
            @JsonProperty("RCPTENDDT")
            val registerEndDateTime: String?,
        )
    }
}

data class SeongdongCultureEventInfoResponse(
    @JsonProperty("param")
    val param: ResultParam,
    @JsonProperty("resultStats")
    val resultStatus: ResultStatus,
    @JsonProperty("resultList")
    val results: List<SeoulOpenCulturalEventDto>,
) {

    data class ResultParam(
        @JsonProperty("now_svc_id")
        val nowSvcId: String,

        @JsonProperty("recent_svc_id")
        val recentSvcId: String,

        @JsonProperty("locale")
        val locale: String,

        @JsonProperty("lang_code")
        val langCode: Int,

        @JsonProperty("sch_svc_list")
        val schSvcList: List<String>,
    )

    data class ResultStatus(
        @JsonProperty("resultCode")
        val code: String,
        @JsonProperty("resultMsg")
        val message: String,
    )

    data class SeoulOpenCulturalEventDto(
        @JsonProperty("SVC_ID")
        val serviceId: String,
        @JsonProperty("SVC_STTUS_CODE")
        val serviceState: String,
        @JsonProperty("MUMM_CL_CODE")
        val mummClassCode: String,
        @JsonProperty("MXMM_CL_CODE")
        val maxmmClassCode: String,
        @JsonProperty("SVC_NM")
        val serviceName: String,
        @JsonProperty("PCHRG_YN")
        val pchrgYn: String,
        @JsonProperty("SELECT_MTH_CODE")
        val selectMthCode: String,
        @JsonProperty("FILE_PARTN_COURS")
        val filePartnCours: String,
        @JsonProperty("FILE_ID")
        val fileId: String,
        @JsonProperty("FILE_EXTSN_NM")
        val fileExtsnNm: String,
        @JsonProperty("MXMM_CL_NM")
        val mxmmClNm: String,
        @JsonProperty("MUMM_CL_NM")
        val mummClNm: String,
    )
}
