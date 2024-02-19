package com.example.productdomain.product.application.dto

import com.example.productdomain.product.domain.ProductStatus

data class ProductEditInput(
    val name: String,
    val price: Int,
    val quantity: Int,
    val status: ProductStatus
) {

}
