package gongback.pureureumserver.domain

/**
 * 수정, 삭제 시 docId를 기준으로 하기 때문에 추가해준 객체
 */
data class SearchResult(
    val data: Any,
    val docId: String
)
