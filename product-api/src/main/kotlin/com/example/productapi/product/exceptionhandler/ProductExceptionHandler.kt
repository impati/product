package com.example.productapi.product.exceptionhandler

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@Slf4j
@RestControllerAdvice
class ProductExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidExceptions(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage = "입력값이 잘못되었습니다.\n"

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, errorMessage))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleArgumentExceptions(exception: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorMessage = "입력값이 잘못되었습니다.\n"

        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, exception.message ?: errorMessage))
    }
}
