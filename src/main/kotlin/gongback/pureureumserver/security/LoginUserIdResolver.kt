package gongback.pureureumserver.security

import gongback.pureureumserver.exception.JwtNotExistsException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserIdResolver(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUserId::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Long {
        val bearerToken = webRequest.getHeader(HttpHeaders.AUTHORIZATION) ?: throw JwtNotExistsException()
        return jwtTokenProvider.getSubject(bearerToken)
    }
}
