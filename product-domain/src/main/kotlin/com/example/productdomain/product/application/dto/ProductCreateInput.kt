package com.example.productdomain.product.application.dto

import com.example.productdomain.product.domain.*

data class ProductCreateInput(
    private val name: String,
    private val price: Int,
    private val quantity: Int
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
