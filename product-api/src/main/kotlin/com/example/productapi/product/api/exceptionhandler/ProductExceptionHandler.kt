package com.example.productapi.product.api.exceptionhandler

import com.example.productdomain.product.exception.ProductOptimisticException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private const val CLIENT_ERROR_MESSAGE = "입력값이 잘못되었습니다."
private const val UNKNOWN_ERROR_MESSAGE = "알 수 없는 예외입니다. 로그를 확인해주세요."

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class ProductExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse<Unit>> {
        log.error("UNKNOWN exception : ${exception.message}")

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, UNKNOWN_ERROR_MESSAGE))
    }

    /**
     * - 요청 필드가 nullable 하지 않는데 null 요청인 경우
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableExceptions(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse<Unit>> {
        log.warn("HttpMessageNotReadableException : ${exception.message}")

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message ?: CLIENT_ERROR_MESSAGE))
    }

    /**
     * - jakarta.validation.constraints 벨리데이션에 실패한 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidExceptions(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse<List<Error>>> {
        log.warn("MethodArgumentNotValidException : ${exception.message}")

        val errorData = exception.fieldErrors
            .map { Error(it.field, it.rejectedValue.toString()) }
            .toList()

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, "Validation failed for argument", errorData))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleArgumentExceptions(exception: IllegalArgumentException): ResponseEntity<ErrorResponse<Unit>> {
        log.warn("IllegalArgumentException : ${exception.message}")

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message ?: CLIENT_ERROR_MESSAGE))
    }

    /**
     * - 상품 수정 시 낙관적 실패인 경우
     */
    @ExceptionHandler(ProductOptimisticException::class)
    fun handleOptimisticLockingFailureExceptions(exception: ProductOptimisticException): ResponseEntity<ErrorResponse<Unit>> {
        log.warn("IllegalArgumentException : ${exception.message}")

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message))
    }
}
