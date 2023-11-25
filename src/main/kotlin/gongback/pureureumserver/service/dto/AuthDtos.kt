package gongback.pureureumserver.service.dto

import gongback.pureureumserver.domain.user.Password
import gongback.pureureumserver.domain.user.User
import gongback.pureureumserver.support.domain.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Past
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

/**
 * Request
 */
@Schema(description = "회원가입 요청")
data class RegisterUserReq(
    @Schema(description = "이메일", minLength = 8, maxLength = 30)
    @field:Length(min = 8, max = 30)
    val email: String,

    @Schema(description = "비밀번호")
    val password: Password,

    @Schema(description = "이름", minLength = 3, maxLength = 5)
    @field:Length(min = 3, max = 5)
    val name: String,

    @Schema(description = "성별", implementation = Gender::class)
    val gender: Gender,

    @Schema(description = "생년월일 (yyyy-MM-dd)")
    @field:Past
    val birthday: LocalDate,

    @Schema(description = "닉네임", minLength = 2, maxLength = 10)
    @field:Length(min = 2, max = 10)
    val nickname: String,
) {
    fun toEntity(): User {
        return User(
            password = password,
            name = name,
            birthday = birthday,
            email = email,
            gender = gender,
            nickname = nickname,
        )
    }
}

@Schema(description = "로그인 요청")
data class LoginReq(
    @Schema(description = "이메일", minLength = 8, maxLength = 30)
    @field:Length(min = 8, max = 30)
    val email: String,

    @Schema(description = "비밀번호")
    val password: Password,
)

@Schema(description = "이메일 중복확인 요청")
data class EmailReq(
    @field:Length(min = 8, max = 30, message = "올바른 형식의 이메일이어야 합니다.")
    val email: String,
)

/**
 * Response
 */
@Schema(description = "JWT 응답")
data class AccessTokenResponse(
    @Schema(description = "access token")
    val accessToken: String,
)

/**
 * DTO
 */
data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
)
