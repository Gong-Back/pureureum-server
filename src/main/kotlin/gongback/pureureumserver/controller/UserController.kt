package gongback.pureureumserver.controller

import gongback.pureureumserver.service.dto.CitizenshipResponse
import gongback.pureureumserver.service.dto.UpdateUserInfoReq
import gongback.pureureumserver.service.dto.UserBadgeResponse
import gongback.pureureumserver.service.dto.UserInfoResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

@Tag(name = "User", description = "사용자 API")
interface UserController {

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "사용자 정보 조회 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun getUserInfo(
        @Parameter(hidden = true) userId: Long,
    ): ResponseEntity<UserInfoResponse>

    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "사용자 정보 업데이트 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun updateUserInfo(
        userInfoReq: UpdateUserInfoReq,
        @Parameter(hidden = true) userId: Long,
    ): ResponseEntity<Unit>

    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "사용자 프로필 이미지 업데이트 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun updateProfile(
        @Schema(description = "프로필 이미지 파일") profile: MultipartFile?,
        @Parameter(hidden = true) userId: Long,
    ): ResponseEntity<Unit>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "사용자 뱃지 정보 조회 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun getUserBadges(
        @Parameter(hidden = true) userId: Long,
    ): ResponseEntity<UserBadgeResponse>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "사용자 시민증 정보 조회 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun getUserCultureCitizenship(
        @Parameter(hidden = true) userId: Long,
    ): ResponseEntity<CitizenshipResponse>
}
