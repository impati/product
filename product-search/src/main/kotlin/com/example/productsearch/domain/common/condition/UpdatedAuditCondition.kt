package com.example.productsearch.domain.common.condition

import com.example.productsearch.condition.Condition
import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import lombok.Getter
import org.springframework.data.elasticsearch.core.query.Criteria

@Getter
data class UpdatedAuditCondition(
    val updatedAt: DateCondition? = null,
    val updatedBy: KeywordCondition? = null
) : Condition {
    override fun buildCriteria(fieldName: String): Criteria {
        val criteria = Criteria()

        updatedAt?.let {
            criteria.and(it.buildCriteria("$fieldName.updatedAt"))
        }
        updatedBy?.let {
            criteria.and(it.buildCriteria("$fieldName.updatedBy"))
        }

        return criteria;
    }
}
