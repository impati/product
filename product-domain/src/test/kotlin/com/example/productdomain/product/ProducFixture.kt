package com.example.productdomain.product

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductName
import com.example.productdomain.product.domain.ProductPrice
import com.example.productdomain.product.domain.ProductQuantity
import java.time.LocalDateTime

fun createDefaultProduct(): Product {
    val memberNumber = "default"
    val now = LocalDateTime.of(2024, 3, 9, 0, 0)

    return Product(
        CreatedAudit(now, memberNumber),
        UpdatedAudit(now, memberNumber),
        ProductName("test"),
        ProductPrice(1000),
        ProductQuantity(100)
    )
}
