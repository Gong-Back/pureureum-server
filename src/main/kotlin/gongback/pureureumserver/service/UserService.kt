package gongback.pureureumserver.service

import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.getMyAttendedCulturalEvents
import gongback.pureureumserver.domain.user.Profile
import gongback.pureureumserver.domain.user.UserBadge
import gongback.pureureumserver.domain.user.UserRepository
import gongback.pureureumserver.domain.user.existsNickname
import gongback.pureureumserver.service.dto.CitizenshipResponse
import gongback.pureureumserver.service.dto.ProfileDto
import gongback.pureureumserver.service.dto.UpdateUserInfoReq
import gongback.pureureumserver.service.dto.UserBadgeResponse
import gongback.pureureumserver.service.dto.UserInfoResponse
import gongback.pureureumserver.support.constant.FilePackage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val fileClient: FileClient,
    private val userRepository: UserRepository,
    private val culturalEventRepository: CulturalEventRepository,
    private val fileKeyGenerator: FileKeyGenerator,
) {

    @Transactional(readOnly = true)
    fun getUserInfoWithProfileUrl(userId: Long): UserInfoResponse {
        val user = userRepository.getReferenceById(userId)
        val profileKey = user.profile.file.fileKey
        val profileUrl = fileClient.getImageUrl(profileKey)
        return UserInfoResponse.of(user, profileUrl)
    }

    @Transactional
    fun updateUserInfo(userId: Long, userInfo: UpdateUserInfoReq) {
        val user = userRepository.getReferenceById(userId)
        userInfo.password?.let {
            user.updatePassword(it)
        }
        userInfo.nickname?.let {
            validateNickname(it)
            user.updateNickname(it)
        }
    }

    @Transactional(readOnly = true)
    fun uploadProfileImage(userId: Long, newProfile: MultipartFile): ProfileDto {
        val user = userRepository.getReferenceById(userId)
        val contentType = MultipartFileExtractor.validateFileType(newProfile)
        if (user.profile.file.originalFileName != Profile.DEFAULT_PROFILE_NAME) {
            fileClient.deleteFile(user.profile.file.fileKey)
        }
        val extension = MultipartFileExtractor.extractExtension(newProfile)
        val fileKey = fileKeyGenerator.generate(FilePackage.PROFILE, extension)
        fileClient.uploadFile(fileKey, newProfile.inputStream, newProfile.size, extension)
        return ProfileDto(fileKey, contentType, newProfile.originalFilename!!)
    }

    @Transactional
    fun updateProfile(userId: Long, profileDto: ProfileDto) {
        val user = userRepository.getReferenceById(userId)
        val newProfile = profileDto.toEntity()
        user.updateProfile(newProfile)
    }

    @Transactional(readOnly = true)
    fun getUserBadges(userId: Long): UserBadgeResponse {
        val user = userRepository.getReferenceById(userId)
        val (lockBadges, unlockBadges) = user.badges.partition { it.file.fileKey == UserBadge.getLockBadgeFileKey() }
        val lockBadgesUrls = lockBadges.map { fileClient.getImageUrl(it.file.fileKey) }
        val unlockBadgeUrls = unlockBadges.map { fileClient.getImageUrl(it.file.fileKey) }
        return UserBadgeResponse.of(lockBadgesUrls, unlockBadgeUrls)
    }

    @Transactional(readOnly = true)
    fun getUserCultureCitizenship(userId: Long): CitizenshipResponse {
        val user = userRepository.getReferenceById(userId)
        require(user.hasCitizenship) { "문화 시민증이 없는 사용자입니다" }
        val profileUrl = fileClient.getImageUrl(user.profile.file.fileKey)
        val attendedCulturalEventCount = culturalEventRepository.getMyAttendedCulturalEvents().count()
        return CitizenshipResponse.of(user, profileUrl, attendedCulturalEventCount)
    }

    private fun validateNickname(it: String) {
        require(!userRepository.existsNickname(it)) { "이미 존재하는 닉네임입니다" }
    }
}
