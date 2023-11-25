package gongback.pureureumserver.controller

import gongback.pureureumserver.exception.JwtNotExistsException
import gongback.pureureumserver.service.AuthService
import gongback.pureureumserver.service.dto.AccessTokenResponse
import gongback.pureureumserver.service.dto.EmailReq
import gongback.pureureumserver.service.dto.LoginReq
import gongback.pureureumserver.service.dto.RegisterUserReq
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthControllerImpl(
    private val authService: AuthService,
) : AuthController {

    @PostMapping("/register")
    override fun register(
        @RequestBody @Valid registerUserReq: RegisterUserReq,
    ): ResponseEntity<Unit> {
        authService.register(registerUserReq)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/validate/email")
    fun checkDuplicatedEmail(
        @RequestBody @Valid emailReq: EmailReq,
    ): ResponseEntity<Unit> {
        authService.checkDuplicatedEmail(emailReq.email)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login")
    override fun login(
        @RequestBody @Valid loginReq: LoginReq,
        servletResponse: HttpServletResponse,
    ): ResponseEntity<AccessTokenResponse> {
        val savedUserId = authService.login(loginReq)
        val tokenRes = authService.getTokenDto(savedUserId)
        CookieProvider.addRefreshTokenToCookie(tokenRes, servletResponse)
        val accessTokenResponse = AccessTokenResponse(tokenRes.accessToken)
        return ResponseEntity.ok(accessTokenResponse)
    }

    @PostMapping("/reissue-token")
    fun reissueToken(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
    ): ResponseEntity<AccessTokenResponse> {
        val bearerToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION) ?: throw JwtNotExistsException()
        val tokenRes = authService.reissueToken(bearerToken)
        CookieProvider.addRefreshTokenToCookie(tokenRes, servletResponse)
        val accessTokenResponse = AccessTokenResponse(tokenRes.accessToken)
        return ResponseEntity.ok(accessTokenResponse)
    }
}
