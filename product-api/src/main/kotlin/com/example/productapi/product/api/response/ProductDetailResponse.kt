package com.example.productapi.product.api.response

import com.example.productdomain.product.domain.ProductStatus
import java.time.LocalDateTime


data class ProductDetailResponse(
    val productId: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val status: ProductStatus,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val updatedBy: String,
    val updatedAt: LocalDateTime,
)
