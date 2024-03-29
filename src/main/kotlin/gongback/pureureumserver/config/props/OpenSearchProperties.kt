package gongback.pureureumserver.config.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@Profile("!test")
@ConfigurationProperties(prefix = "open-search")
data class OpenSearchProperties(
    val hostname: String,
    val username: String,
    val password: String,
    val port: Int,
    val scheme: String,
)
