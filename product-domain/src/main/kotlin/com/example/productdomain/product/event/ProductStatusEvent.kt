package com.example.productdomain.product.event

import com.example.productdomain.product.domain.Product

data class ProductStatusEvent(
    val product: Product
)
