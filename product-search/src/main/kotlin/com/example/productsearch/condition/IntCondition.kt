package com.example.productsearch.condition

import com.example.productsearch.condition.operator.NumberOperator

data class IntCondition(
    val value: Int,
    val operator: NumberOperator
) {
}
