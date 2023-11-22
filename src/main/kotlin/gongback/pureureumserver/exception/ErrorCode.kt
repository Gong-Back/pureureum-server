package gongback.pureureumserver.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val code: Int, val errorMessage: String) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10001, "Internal server error"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -11000, "Invalid request"),
}
