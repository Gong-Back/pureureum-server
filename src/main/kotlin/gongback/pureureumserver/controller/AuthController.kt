package gongback.pureureumserver.controller

import gongback.pureureumserver.service.dto.AccessTokenResponse
import gongback.pureureumserver.service.dto.LoginReq
import gongback.pureureumserver.service.dto.RegisterUserReq
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Auth", description = "인증 API")
interface AuthController {

    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 / 이미 존재하는 사용자 정보일 경우",
        ),
    )
    fun register(
        registerUserReq: RegisterUserReq,
    ): ResponseEntity<Unit>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 / 사용자 정보가 존재하지 않을 경우 / 비밀번호가 일치하지 않는 경우",
        ),
        ApiResponse(
            responseCode = "430",
            description = "유효하지 않은 토큰일 경우",
        ),
        ApiResponse(
            responseCode = "431",
            description = "만료된 토큰일 경우",
        ),
    )
    fun login(
        loginReq: LoginReq,
        servletResponse: HttpServletResponse,
    ): ResponseEntity<AccessTokenResponse>
}
