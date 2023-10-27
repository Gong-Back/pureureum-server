package gongback.pureureumserver.domain.user

enum class UserRole(
    private val description: String,
) {
    ROLE_USER("유저"),
    ROLE_ADMIN("관리자"),
}
