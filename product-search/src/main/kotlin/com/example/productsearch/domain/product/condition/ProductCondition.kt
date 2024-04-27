package com.example.productsearch.domain.product.condition

import com.example.productsearch.condition.KeywordCondition
import com.example.productsearch.condition.NumberCondition
import com.example.productsearch.condition.TextCondition
import com.example.productsearch.domain.common.condition.CreatedAuditCondition
import com.example.productsearch.domain.common.condition.UpdatedAuditCondition
import lombok.Getter
import org.springframework.data.elasticsearch.core.query.Criteria

@Getter
data class ProductCondition(
    val productId: NumberCondition? = null,
    val name: TextCondition? = null,
    val createdAudit: CreatedAuditCondition? = null,
    val updatedAudit: UpdatedAuditCondition? = null,
    val price: NumberCondition? = null,
    val quantity: NumberCondition? = null,
    val status: KeywordCondition? = null
) {
    fun buildCriteria(): Criteria {
        val criteria = Criteria()

        productId?.let {
            criteria.and(it.buildCriteria("productId"))
        }
        name?.let {
            criteria.and(it.buildCriteria("name"))
        }
        createdAudit?.let {
            criteria.subCriteria(it.buildCriteria("createdAudit"))
        }
        updatedAudit?.let {
            criteria.subCriteria(it.buildCriteria("updatedAudit"))
        }
        price?.let {
            criteria.and(it.buildCriteria("price"))
        }
        quantity?.let {
            criteria.and(it.buildCriteria("quantity"))
        }
        status?.let {
            criteria.and(it.buildCriteria("status"))
        }

        return criteria;
    }
}
