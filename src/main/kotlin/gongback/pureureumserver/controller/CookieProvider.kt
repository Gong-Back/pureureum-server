package gongback.pureureumserver.controller

import gongback.pureureumserver.service.dto.TokenDto
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie

private const val REFRESH_TOKEN_COOKIE_KEY = "refreshToken"
private const val NONE = "None"

class CookieProvider {

    companion object {
        fun addRefreshTokenToCookie(tokenDto: TokenDto, response: HttpServletResponse) {
            val refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_KEY, tokenDto.refreshToken)
                .sameSite(NONE)
                .httpOnly(true)
                .secure(true)
                .build()
                .toString()
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie)
        }
    }
}
