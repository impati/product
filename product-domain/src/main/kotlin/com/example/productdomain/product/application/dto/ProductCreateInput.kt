package com.example.productdomain.product.application.dto

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.*

data class ProductCreateInput(
    val name: String,
    val price: Int,
    val quantity: Int,
    val imagePath: String,
    val createdAudit: CreatedAudit,
    val updatedAudit: UpdatedAudit,
) {

    fun toProduct(): Product {
        return Product(
            createdAudit,
            updatedAudit,
            ProductName(name),
            ProductPrice(price),
            ProductQuantity(quantity),
            ImagePath(imagePath),
            ProductStatus.PRE_REGISTRATION
        );
    }
}
