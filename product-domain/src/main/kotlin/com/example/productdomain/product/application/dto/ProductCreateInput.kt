package com.example.productdomain.product.application.dto

import com.example.productdomain.product.domain.*

data class ProductCreateInput(
    val name: String,
    val price: Int,
    val quantity: Int
) {

    fun toProduct(): Product {
        return Product(
            ProductName(name),
            ProductPrice(price),
            ProductQuantity(quantity),
            ProductStatus.PRE_REGISTRATION
        );
    }
}
