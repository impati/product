package com.example.productdomain.product.exception

open class ProductException(
    override val message: String
) : RuntimeException(message) {
}
