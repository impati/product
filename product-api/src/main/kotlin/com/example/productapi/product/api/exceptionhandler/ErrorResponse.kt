package com.example.productapi.product.api.exceptionhandler

class ErrorResponse<T>(
    val statusCode: String,
    val message: String,
    val errorData: T? = null
) {
}
