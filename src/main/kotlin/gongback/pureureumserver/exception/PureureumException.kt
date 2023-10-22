package gongback.pureureumserver.exception

open class PureureumException(message: String? = null, cause: Throwable? = null, val errorCode: ErrorCode) :
    RuntimeException(message ?: errorCode.errorMessage, cause)

class OpenSearchException(cause: Throwable? = null) :
    PureureumException(cause = cause, errorCode = ErrorCode.OPEN_SEARCH_FAILED)
