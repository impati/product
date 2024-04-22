package com.example.productapi.product.api.request

import com.example.productsearch.condition.operator.NumberOperator

data class NumberOperateRequest(
    val price: Int,
    val operator: NumberOperator
) {
}
