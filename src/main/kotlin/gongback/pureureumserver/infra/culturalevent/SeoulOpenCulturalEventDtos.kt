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
