package gongback.pureureumserver.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val key: String = "",
    val expiredTimeMs: Long = 0L,
    val refreshExpiredTimeMs: Long = 0L,
)
