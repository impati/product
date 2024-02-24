package com.example.productapi.product.controller.request

import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.ProductStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class ProductEditRequest(

    @field:NotBlank
    val name: String,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val price: Int,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val quantity: Int,
    val status: ProductStatus,
) {

    fun toInput(): ProductEditInput {
        return ProductEditInput(
            name,
            price,
            quantity,
            status
        )
    }
}
