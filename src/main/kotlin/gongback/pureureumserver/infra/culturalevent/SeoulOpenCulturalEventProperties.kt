package gongback.pureureumserver.infra.culturalevent

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@Profile("!test")
@ConfigurationProperties(prefix = "cultural-event-open-api")
data class SeoulOpenCulturalEventProperties(
    val key: String,
    val type: String,
) {
    val baseUrl = "http://openAPI.seoul.go.kr:8088/$key/$type/"
}
