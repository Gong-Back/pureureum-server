package gongback.pureureumserver.config

import gongback.pureureumserver.support.constant.AUTHORIZATION_HEADER_NAME
import gongback.pureureumserver.support.constant.BEARER
import gongback.pureureumserver.support.constant.JWT
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig {
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
                    .scheme(BEARER)
                    .bearerFormat(JWT),
            )
        }

        return OpenAPI()
            .info(info)
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}
