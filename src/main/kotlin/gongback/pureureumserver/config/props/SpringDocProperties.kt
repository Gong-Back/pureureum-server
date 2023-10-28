package gongback.pureureumserver.config.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@Profile("!test")
@ConfigurationProperties(prefix = "springdoc")
data class SpringDocProperties(
    val apiUrl: String,
    val scheme: String,
    val bearerFormat: String,
)
