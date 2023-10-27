package gongback.pureureumserver.exception

open class ErrorResponse private constructor(val code: Int, val errorMessage: String) {
    companion object {
        fun of(errorCode: ErrorCode, errorMessage: String? = null) =
            ErrorResponse(errorCode.code, errorMessage ?: errorCode.errorMessage)
    }
}
