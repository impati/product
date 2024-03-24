package com.example.productdomain.product.exception

class ProductOptimisticException(
    override val message: String
) : ProductException(message) {
}
