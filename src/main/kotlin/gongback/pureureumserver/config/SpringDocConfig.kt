package gongback.pureureumserver.config

import gongback.pureureumserver.config.props.SpringDocProperties
import gongback.pureureumserver.support.constant.AUTHORIZATION_HEADER_NAME
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!test")
@Configuration(proxyBeanMethods = false)
class SpringDocConfig(
    private val springDocProperties: SpringDocProperties,
) {
    @Bean
    fun openAPI(): OpenAPI {
        val info = Info().apply {
            title("Pureureum Server")
            description("Pureureum Server API")
            version("1.0.0")
        }

        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(AUTHORIZATION_HEADER_NAME)
        val components = Components().apply {
            addSecuritySchemes(
                AUTHORIZATION_HEADER_NAME,
                SecurityScheme()
                    .name(AUTHORIZATION_HEADER_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(springDocProperties.scheme)
                    .bearerFormat(springDocProperties.bearerFormat),
            )
        }

        return OpenAPI()
            .servers(getServers())
            .info(info)
            .addSecurityItem(securityRequirement)
            .components(components)
    }

    private fun getServers() = listOf(Server().apply { url(springDocProperties.apiUrl) })
}
