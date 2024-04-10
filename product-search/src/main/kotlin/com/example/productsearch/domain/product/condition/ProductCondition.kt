package com.example.productsearch.domain.product.condition

import com.example.productsearch.condition.IntCondition
import com.example.productsearch.condition.KeywordCondition
import com.example.productsearch.condition.TextCondition
import com.example.productsearch.domain.common.condition.CreatedAuditCondition
import com.example.productsearch.domain.common.condition.UpdatedAuditCondition
import lombok.Getter

@Getter
data class ProductCondition(
    val productId: IntCondition,
    val productName: TextCondition,
    val createdAudit: CreatedAuditCondition,
    val updatedAuditCondition: UpdatedAuditCondition,
    val price: IntCondition,
    val quantity: IntCondition,
    val status: KeywordCondition
) {
}
