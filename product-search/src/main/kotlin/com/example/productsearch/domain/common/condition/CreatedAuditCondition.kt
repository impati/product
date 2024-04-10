package com.example.productsearch.domain.common.condition

import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import lombok.Getter

@Getter
data class CreatedAuditCondition(
    val createdAt: DateCondition,
    val createdBy: KeywordCondition
) {
}
