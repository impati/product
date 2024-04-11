package com.example.productsearch.condition

import org.springframework.data.elasticsearch.core.query.Criteria

interface Condition {
    fun buildCriteria(fieldName: String): Criteria
}
