package com.example.productapi.product.api.request

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.dto.ProductCreateInput
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank


data class ProductCreateRequest(

    @field:NotBlank
    val name: String,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val price: Int,

    @field:Min(0)
    @field:Max(10_000_000_000)
    val quantity: Int,

    @field:NotBlank
    val memberNumber: String
) {
    fun toInput(createdAudit: CreatedAudit): ProductCreateInput {
        return ProductCreateInput(
            name,
            price,
            quantity,
            createdAudit,
            UpdatedAudit(createdAudit.createdAt, createdAudit.createdBy)
        )
    }
}
