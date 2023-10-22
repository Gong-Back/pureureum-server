package gongback.pureureumserver.infra.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.open-search")
class OpenSearchProperties(
    val hostname: String = "",
    val port: Int = 0,
    val scheme: String = "",
    val username: String = "",
    val password: String = ""
)
