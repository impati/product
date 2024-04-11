package com.example.productsearch.condition

import org.springframework.data.elasticsearch.core.query.Criteria

data class TextCondition(
    val value: String
) : Condition {

    override fun buildCriteria(fieldName: String): Criteria {
        return Criteria(fieldName).contains(value)
    }
}

