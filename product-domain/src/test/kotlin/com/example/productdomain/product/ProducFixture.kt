package com.example.productdomain.product

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.*
import java.time.LocalDateTime

fun createDefaultProduct(): Product {
    val memberNumber = "default"
    val now = LocalDateTime.of(2024, 3, 9, 0, 0)

    return Product(
        CreatedAudit(now, memberNumber),
        UpdatedAudit(now, memberNumber),
        ProductName("test"),
        ProductPrice(1000),
        ProductQuantity(100),
        ProductStatus.PRE_REGISTRATION,
        0L
    )
}
