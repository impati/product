package com.example.productsearch.domain.common.condition

import com.example.productsearch.condition.Condition
import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import lombok.Getter
import org.springframework.data.elasticsearch.core.query.Criteria

@Getter
data class CreatedAuditCondition(
    val createdAt: DateCondition? = null,
    val createdBy: KeywordCondition? = null
) : Condition {
    override fun buildCriteria(fieldName: String): Criteria {
        val criteria = Criteria()

        createdAt?.let {
            criteria.and(it.buildCriteria("$fieldName.createdAt"))
        }
        createdBy?.let {
            criteria.and(it.buildCriteria("$fieldName.createdBy"))
        }

        return criteria;
    }
}
