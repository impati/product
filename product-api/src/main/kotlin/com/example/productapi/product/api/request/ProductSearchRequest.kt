package com.example.productapi.product.api.request

import com.example.productdomain.product.domain.ProductStatus


data class ProductSearchRequest(

    val createdBy: String? = null,
    val productName: String? = null,
    val productPrice: NumberOperateRequest? = null,
    val productStatus: ProductStatus? = null,
    val productId: Long? = null,
    val productCreatedTime: BetweenDateTimeRequest? = null
)
