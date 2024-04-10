package com.example.productsearch.condition

import com.example.productsearch.condition.operator.DateOperator
import java.time.LocalDateTime

data class DateCondition(
    private val startAt: LocalDateTime?,
    private val endAt: LocalDateTime?,
    private val at: LocalDateTime?,
    private val operator: DateOperator
) {

    companion object {

        fun ofBetween(startAt: LocalDateTime, endAt: LocalDateTime): DateCondition {
            return DateCondition(startAt, endAt, null, DateOperator.BETWEEN)
        }

        fun from(at: LocalDateTime, operator: DateOperator): DateCondition {
            return DateCondition(null, null, at, operator)
        }
    }
}
