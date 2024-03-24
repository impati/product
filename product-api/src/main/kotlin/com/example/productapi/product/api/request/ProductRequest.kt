package com.example.productapi.product.api.request

import jakarta.validation.constraints.NotBlank

data class ProductRequest(

    @field:NotBlank
    val memberNumber: String
)
