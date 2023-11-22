package gongback.pureureumserver.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error("[IllegalArgumentException]: ${ex.message}", ex)
        val invalidRequestErrorCode = ErrorCode.INVALID_REQUEST
        return ResponseEntity.status(invalidRequestErrorCode.httpStatus)
            .body(ErrorResponse.of(invalidRequestErrorCode, ex.message))
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.error("[RuntimeException]: ${ex.message}", ex)
        val internalServerErrorCode = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(internalServerErrorCode.httpStatus)
            .body(ErrorResponse.of(internalServerErrorCode, ex.message))
    }

    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[Exception]: ${ex.message}", ex)
        val internalServerErrorCode = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(internalServerErrorCode.httpStatus)
            .body(ErrorResponse.of(internalServerErrorCode, ex.message))
    }
}
