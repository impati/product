package com.example.productdomain.product

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
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
        ImagePath("https://localhost"),
        ProductStatus.PRE_REGISTRATION,
        0L
    )
}

fun createDefaultProduct(
    productStatus: ProductStatus = ProductStatus.PRE_REGISTRATION
): Product {
    val memberNumber = "default"
    val now = LocalDateTime.of(2024, 3, 9, 0, 0)

    return Product(
        CreatedAudit(now, memberNumber),
        UpdatedAudit(now, memberNumber),
        ProductName("test"),
        ProductPrice(1000),
        ProductQuantity(100),
        ImagePath("https://localhost"),
        productStatus,
        0L
    )
}
