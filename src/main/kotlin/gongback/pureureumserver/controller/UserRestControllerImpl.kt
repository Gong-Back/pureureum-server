package gongback.pureureumserver.controller

import gongback.pureureumserver.security.LoginUserId
import gongback.pureureumserver.service.UserService
import gongback.pureureumserver.service.dto.CitizenshipResponse
import gongback.pureureumserver.service.dto.UpdateUserInfoReq
import gongback.pureureumserver.service.dto.UserBadgeResponse
import gongback.pureureumserver.service.dto.UserInfoResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/users")
class UserRestControllerImpl(
    private val userService: UserService,
) : UserController {

    @GetMapping("/me")
    override fun getUserInfo(
        @LoginUserId userId: Long,
    ): ResponseEntity<UserInfoResponse> {
        val userInfo = userService.getUserInfoWithProfileUrl(userId)
        return ResponseEntity.ok().body(userInfo)
    }

    @PostMapping("/update/info")
    override fun updateUserInfo(
        @RequestBody @Valid userInfoReq: UpdateUserInfoReq,
        @LoginUserId userId: Long,
    ): ResponseEntity<Unit> {
        userService.updateUserInfo(userId, userInfoReq)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/update/profile")
    override fun updateProfile(
        @RequestPart profile: MultipartFile?,
        @LoginUserId userId: Long,
    ): ResponseEntity<Unit> {
        profile?.let {
            val profileDto = userService.uploadProfileImage(userId, it)
            userService.updateProfile(userId, profileDto)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/badges")
    override fun getUserBadges(
        @LoginUserId userId: Long,
    ): ResponseEntity<UserBadgeResponse> {
        val userBadges = userService.getUserBadges(userId)
        return ResponseEntity.ok().body(userBadges)
    }

    @GetMapping("/culture-citizenship")
    override fun getUserCultureCitizenship(
        @LoginUserId userId: Long,
    ): ResponseEntity<CitizenshipResponse> {
        val citizenship = userService.getUserCultureCitizenship(userId)
        return ResponseEntity.ok().body(citizenship)
    }
}
