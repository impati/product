package com.example.productapi.product.api.exceptionhandler

import com.example.productdomain.product.exception.ProductOptimisticException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException

import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


private const val CLIENT_ERROR_MESSAGE = "입력값이 잘못되었습니다."
private const val UNKNOWN_ERROR_MESSAGE = "알 수 없는 예외입니다. 로그를 확인해주세요."

@Slf4j
@RestControllerAdvice
class ProductExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        println(exception)

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, UNKNOWN_ERROR_MESSAGE))
    }

    /**
     * - 요청 필드가 nullable 하지 않는데 null 요청인 경우
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableExceptions(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message ?: CLIENT_ERROR_MESSAGE))
    }

    /**
     * - jakarta.validation.constraints 벨리데이션에 실패한 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidExceptions(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleArgumentExceptions(exception: IllegalArgumentException): ResponseEntity<ErrorResponse> {

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message ?: CLIENT_ERROR_MESSAGE))
    }

    /**
     * - 상품 수정 시 낙관적 실패인 경우
     */
    @ExceptionHandler(ProductOptimisticException::class)
    fun handleOptimisticLockingFailureExceptions(exception: ProductOptimisticException): ResponseEntity<ErrorResponse> {

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message))
    }
}
