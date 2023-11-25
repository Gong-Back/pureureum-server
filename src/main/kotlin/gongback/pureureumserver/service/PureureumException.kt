package gongback.pureureumserver.service

import gongback.pureureumserver.exception.ErrorCode

open class PureureumException(val errorCode: ErrorCode, errorMessage: String) : RuntimeException(errorMessage)
class ForbiddenException(message: String) : PureureumException(ErrorCode.FORBIDDEN, message)
