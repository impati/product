package com.example.productsearch.condition

import com.example.productsearch.condition.operator.NumberOperator
import org.springframework.data.elasticsearch.core.query.Criteria

data class IntCondition(
    val value: Int,
    val operator: NumberOperator
) : Condition {

    override fun buildCriteria(fieldName: String): Criteria {
        val criteria = Criteria(fieldName)
        return when (operator) {
            NumberOperator.LESS_THAN -> criteria.lessThan(value)
            NumberOperator.MORE_THEN -> criteria.greaterThan(value)
            NumberOperator.EQUAL -> criteria.`is`(value)
            NumberOperator.LESS_THAN_OR_EQUAL -> criteria.lessThanEqual(value)
            NumberOperator.MORE_THAN_OR_EQUAL -> criteria.greaterThanEqual(value)
        }
    }
}
