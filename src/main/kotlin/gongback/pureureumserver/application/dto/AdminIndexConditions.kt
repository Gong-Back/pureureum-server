package gongback.pureureumserver.application.dto

import java.util.Locale

data class IndexSaveRequest(
    val index: String = "",
    val fields: Map<String, FieldType> = emptyMap()
)

data class IndexDeleteRequest(
    val index: String = ""
)

data class IndexPropertyDeleteRequest(
    val baseIndex: String = "",
    val newIndex: String = "",
    val propertyName: String = ""
)

enum class FieldType(
    val description: String
) {
    INTEGER("정수 타입"),
    KEYWORD("문자 타입"),
    BOOLEAN("Boolean 타입"),
    DATE("날짜 타입")
    ;

    fun toLowerCase(): String {
        return this.name.lowercase(Locale.getDefault())
    }
}

data class IndexSaveResponse(
    val index: String
)

data class IndexResultResponse(
    val isSuccess: Boolean
)
