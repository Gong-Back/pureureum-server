package gongback.pureureumserver.exception

import gongback.pureureumserver.service.ForbiddenException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error("[IllegalArgumentException]: ${ex.message}", ex)
        val invalidRequestErrorCode = ErrorCode.INVALID_REQUEST
        return ResponseEntity.status(invalidRequestErrorCode.httpStatus)
            .body(ErrorResponse.of(invalidRequestErrorCode, ex.message))
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbiddenException(ex: ForbiddenException): ResponseEntity<ErrorResponse> {
        logger.error("[ForbiddenException]: ${ex.message}", ex)
        val forbiddenErrorCode = ErrorCode.FORBIDDEN
        return ResponseEntity.status(forbiddenErrorCode.httpStatus)
            .body(ErrorResponse.of(forbiddenErrorCode, ex.message))
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(ex: NoSuchElementException): ResponseEntity<ErrorResponse> {
        logger.error("[NoSuchElementException]: ${ex.message}", ex)
        val notFoundErrorCode = ErrorCode.NOT_FOUND_RESOURCE
        return ResponseEntity.status(notFoundErrorCode.httpStatus)
            .body(ErrorResponse.of(notFoundErrorCode, ex.message))
    }

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(
        ex: JwtException,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Any> {
        logger.warn("[JwtException] ", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ex.code, ex.message))
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
