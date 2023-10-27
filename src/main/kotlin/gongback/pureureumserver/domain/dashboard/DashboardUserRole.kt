package gongback.pureureumserver.domain.dashboard

enum class DashboardUserRole(
    private val description: String,
) {
    MANAGER("운영자"),
    MEMBER("일반 회원"),
}
