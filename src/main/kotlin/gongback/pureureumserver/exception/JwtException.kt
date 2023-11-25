package gongback.pureureumserver.exception

open class JwtException(val code: ErrorCode) : RuntimeException(code.errorMessage)

class JwtNotExistsException(code: ErrorCode = ErrorCode.JWT_NOT_EXISTS) : JwtException(code)

class JwtNotValidException(code: ErrorCode = ErrorCode.JWT_NOT_VALID) : JwtException(code)

class JwtExpiredException(code: ErrorCode = ErrorCode.JWT_EXPIRED) : JwtException(code)
