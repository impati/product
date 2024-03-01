package com.example.productapi.product.api.request

import com.example.productdomain.product.application.dto.ProductCreateInput
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank


class ProductCreateRequest(

    @field:NotBlank
    val name: String,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val price: Int,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val quantity: Int
) {
    fun toInput(): ProductCreateInput {
        return ProductCreateInput(name, price, quantity)
    }
}
