package gongback.pureureumserver.service.dto

import gongback.pureureumserver.domain.user.Password
import gongback.pureureumserver.domain.user.Profile
import gongback.pureureumserver.domain.user.User
import gongback.pureureumserver.support.domain.Gender
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import java.net.URL
import java.time.LocalDate

/**
 * Request
 */
@Schema(description = "사용자 정보 업데이트 요청")
data class UpdateUserInfoReq(
    @Schema(description = "비밀번호", required = false)
    val password: Password?,

    @Schema(description = "닉네임", minLength = 2, maxLength = 10, required = false)
    @field:Length(min = 2, max = 10, message = "닉네임은 2~10글자여야 합니다")
    val nickname: String?,
)

/**
 * Response
 */
@Schema(description = "사용자 정보 응답")
data class UserInfoResponse private constructor(
    @Schema(description = "이메일")
    val email: String,
    @Schema(description = "이름")
    val name: String,
    @Schema(description = "닉네임")
    val nickname: String,
    @Schema(description = "성별")
    val gender: Gender,
    @Schema(description = "생년월일")
    val birthday: LocalDate,
    @Schema(description = "프로필 이미지 URL")
    val profileUrl: URL,
) {
    companion object {
        fun of(user: User, profileUrl: URL): UserInfoResponse {
            return UserInfoResponse(
                email = user.email,
                name = user.name,
                nickname = user.nickname,
                gender = user.gender,
                birthday = user.birthday,
                profileUrl = profileUrl,
            )
        }
    }
}

@Schema(description = "사용자 뱃지 정보 응답")
data class UserBadgeResponse private constructor(
    val badgeUrl: List<BadgeRes>,
) {
    companion object {
        fun of(lockBadgesUrls: List<URL>, unlockBadgeUrls: List<URL>): UserBadgeResponse {
            val lockBadges = lockBadgesUrls.map {
                BadgeRes(it.toString(), true)
            }
            val unlockBadges = unlockBadgeUrls.map {
                BadgeRes(it.toString(), false)
            }
            return UserBadgeResponse(lockBadges + unlockBadges)
        }
    }

    data class BadgeRes(
        @Schema(description = "뱃지 이미지 URL")
        val badgeUrl: String,
        @Schema(description = "뱃지 잠김 여부")
        val isLocked: Boolean,
    )
}

@Schema(description = "사용자 문화 시민증 정보 응답")
data class CitizenshipResponse private constructor(
    @Schema(description = "닉네임")
    val nickname: String,
    @Schema(description = "이름")
    val name: String,
    @Schema(description = "가입일")
    val joinDate: LocalDate,
    @Schema(description = "프로필 이미지 URL")
    val profileUrl: URL,
    @Schema(description = "문화 컨텐츠 참여 횟수")
    val attendCount: Int,
) {
    companion object {
        fun of(user: User, profileUrl: URL): CitizenshipResponse {
            return CitizenshipResponse(
                nickname = user.nickname,
                name = user.name,
                joinDate = user.createdDate.toLocalDate(),
                profileUrl = profileUrl,
                // TODO 이거 컨텐츠 참여 횟수 넣어야 함
                attendCount = 0,
            )
        }
    }
}

/**
 * DTO
 */
data class ProfileDto(
    val fileKey: String,
    val contentType: String,
    val originalFileName: String,
) {
    fun toEntity(): Profile = Profile(
        fileKey,
        contentType,
        originalFileName,
    )
}
