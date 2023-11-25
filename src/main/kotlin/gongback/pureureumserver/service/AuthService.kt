package gongback.pureureumserver.service

import gongback.pureureumserver.domain.user.UserRepository
import gongback.pureureumserver.domain.user.existsEmail
import gongback.pureureumserver.domain.user.getUserByEmail
import gongback.pureureumserver.security.JwtTokenProvider
import gongback.pureureumserver.service.dto.LoginReq
import gongback.pureureumserver.service.dto.RegisterUserReq
import gongback.pureureumserver.service.dto.TokenDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Transactional
    fun register(registerUserReq: RegisterUserReq) {
        checkDuplicatedEmail(registerUserReq.email)
        userRepository.save(registerUserReq.toEntity())
    }

    fun checkDuplicatedEmail(email: String) {
        require(userRepository.existsEmail(email).not()) { "이미 가입된 사용자입니다" }
    }

    fun login(loginReq: LoginReq): Long {
        val findUser = userRepository.getUserByEmail(loginReq.email)
        findUser.authenticate(loginReq.password)
        return findUser.id
    }

    fun getTokenDto(userId: Long) =
        TokenDto(jwtTokenProvider.createToken(userId), jwtTokenProvider.createRefreshToken(userId))

    fun reissueToken(bearerToken: String): TokenDto {
        val userId = jwtTokenProvider.getSubject(bearerToken)
        return TokenDto(jwtTokenProvider.createToken(userId), jwtTokenProvider.createRefreshToken(userId))
    }
}
