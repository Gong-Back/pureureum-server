package gongback.pureureumserver.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(PureureumException::class)
    fun handlePureureumException(ex: PureureumException): ResponseEntity<ErrorResponse> {
        logger.warn("[PureureumException] ${ex.message}", ex)
        return ResponseEntity.status(ex.errorCode.httpStatus)
            .body(ErrorResponse.of(ex.errorCode, ex.message))
    }

    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[Exception]", ex)
        val internalServerErrorCode = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(internalServerErrorCode.httpStatus)
            .body(ErrorResponse.of(internalServerErrorCode))
    }
}
