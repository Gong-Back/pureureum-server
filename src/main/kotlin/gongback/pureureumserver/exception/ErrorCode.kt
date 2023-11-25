package gongback.pureureumserver.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val code: Int, val errorMessage: String) {
    JWT_NOT_EXISTS(HttpStatus.BAD_REQUEST, 430, "토큰이 존재하지 않습니다"),
    JWT_NOT_VALID(HttpStatus.BAD_REQUEST, 430, "유효하지 않은 토큰입니다"),
    JWT_EXPIRED(HttpStatus.BAD_REQUEST, 431, "만료된 토큰입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10001, "Internal server error"),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -11000, "Invalid request"),
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, -11001, "Not found resource"),
    FORBIDDEN(HttpStatus.FORBIDDEN, -11002, "Forbidden"),
}
