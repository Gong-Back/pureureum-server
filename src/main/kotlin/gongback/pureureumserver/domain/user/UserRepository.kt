package gongback.pureureumserver.domain.user

import org.springframework.data.jpa.repository.JpaRepository

fun UserRepository.getUserByEmail(email: String): User =
    findByInformationEmail(email) ?: throw NoSuchElementException("요청하신 사용자 정보를 찾을 수 없습니다")

fun UserRepository.existsEmail(email: String): Boolean = existsByInformationEmail(email)

fun UserRepository.existsNickname(nickname: String): Boolean = existsByInformationNickname(nickname)

interface UserRepository : JpaRepository<User, Long> {
    fun existsByInformationEmail(email: String): Boolean
    fun findByInformationEmail(email: String): User?
    fun existsByInformationNickname(nickname: String): Boolean
}
