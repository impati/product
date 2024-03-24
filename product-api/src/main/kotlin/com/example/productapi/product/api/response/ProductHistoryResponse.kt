package com.example.productapi.product.api.response

import com.example.productdomain.product.domain.ProductHistory
import com.example.productdomain.product.domain.ProductStatus
import java.time.LocalDateTime

data class ProductHistoryResponse(

    val createdAt: LocalDateTime,
    val createdBy: String,
    val name: String,
    val price: Int,
    val status: ProductStatus
) {

    companion object {
        fun from(productHistory: ProductHistory): ProductHistoryResponse {
            return ProductHistoryResponse(
                productHistory.createdAudit.createdAt,
                productHistory.createdAudit.createdBy,
                productHistory.name,
                productHistory.price,
                productHistory.status
            )
        }
    }
}
