package gongback.pureureumserver.domain.suggestion

enum class SuggestionStatus(private val description: String) {
    IN_PROGRESS("진행중"),
    FINISHED("종료");
}
