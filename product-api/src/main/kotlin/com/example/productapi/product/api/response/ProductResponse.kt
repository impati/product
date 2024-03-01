package com.example.productapi.product.api.response

import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductStatus

class ProductResponse(
    val productId: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val status: ProductStatus
) {

    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                product.id!!,
                product.name.value,
                product.price.value,
                product.quantity.value,
                product.status
            )
        }
    }
}
