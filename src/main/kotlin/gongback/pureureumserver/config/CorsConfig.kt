package gongback.pureureumserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
//    private val permitOrigins: List<String> = listOf(
//        "http://localhost:3000",
//        // TODO: Front 서버 도메인 추가
//    )

    private val permitHttpMethods: List<HttpMethod> = listOf(
        HttpMethod.GET,
        HttpMethod.HEAD,
        HttpMethod.POST,
        HttpMethod.PUT,
        HttpMethod.PATCH,
        HttpMethod.DELETE,
        HttpMethod.OPTIONS,
    )

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/**")
            .allowedOriginPatterns("*")
            .allowedMethods(*permitHttpMethods.map(HttpMethod::name).toTypedArray())
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}
