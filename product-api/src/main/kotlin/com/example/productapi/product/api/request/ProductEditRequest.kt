package com.example.productapi.product.api.request

import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.ProductStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductEditRequest(

    @field:NotBlank
    val name: String,

    @field:Min(0)
    @field:Max(10_000_000_000)
    @field:NotNull
    val price: Int?,

    @field:Min(0)
    @field:Max(10_000_000_000)
    @field:NotNull
    val quantity: Int?,

    @field:NotNull
    val status: ProductStatus,

    @field:NotBlank
    val memberNumber: String,

    @field:NotBlank
    val imagePath: String,

    @field:NotNull
    val version: Long?
) {

    fun toInput(): ProductEditInput {
        return ProductEditInput(
            name,
            price!!,
            quantity!!,
            status,
            imagePath,
            version!!
        )
    }
}
