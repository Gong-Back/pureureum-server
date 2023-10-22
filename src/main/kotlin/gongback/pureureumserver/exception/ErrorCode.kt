package gongback.pureureumserver.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val code: Int, val errorMessage: String) {
    OPEN_SEARCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 512, "Open search failed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10001, "Internal server error"),
}
