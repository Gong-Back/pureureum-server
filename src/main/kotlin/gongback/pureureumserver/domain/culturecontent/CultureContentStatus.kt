package gongback.pureureumserver.domain.culturecontent

enum class CultureContentStatus(
    private val description: String
) {
    ADMIN_REQUIRED("관리자 승인 필요"),
    REJECTED("거절"),
    PREPARING("준비중"),
    RECRUITING("모집중"),
    COMPLETED("모집완료")
}
