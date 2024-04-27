package com.example.productsearch.condition

import com.example.productsearch.condition.operator.DateOperator
import org.springframework.data.elasticsearch.core.query.Criteria
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DateCondition(
    private val startAt: LocalDateTime?,
    private val endAt: LocalDateTime?,
    private val at: LocalDateTime?,
    private val operator: DateOperator
) : Condition {

    override fun buildCriteria(fieldName: String): Criteria {
        val criteria = Criteria(fieldName)

        when (operator) {
            DateOperator.BETWEEN -> {
                if (startAt != null && endAt != null) {
                    criteria.between(convertISOFormat(startAt), convertISOFormat(endAt))
                }
            }

            DateOperator.AFTER -> at?.let {
                criteria.greaterThan(convertISOFormat(it))
            }

            DateOperator.BEFORE -> at?.let {
                criteria.lessThan(convertISOFormat(it))
            }

            DateOperator.AFTER_OR_EQUAL -> at?.let {
                criteria.greaterThanEqual(convertISOFormat(it))
            }

            DateOperator.BEFORE_OR_EQUAL -> at?.let {
                criteria.lessThanEqual(convertISOFormat(it))
            }

            DateOperator.EQUAL -> at?.let {
                criteria.`is`(convertISOFormat(it))
            }
        }

        return criteria
    }

    private fun convertISOFormat(at: LocalDateTime): String = at.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    companion object {

        fun ofBetween(startAt: LocalDateTime, endAt: LocalDateTime): DateCondition {
            return DateCondition(startAt, endAt, null, DateOperator.BETWEEN)
        }

        fun from(at: LocalDateTime, operator: DateOperator): DateCondition {
            return DateCondition(null, null, at, operator)
        }
    }
}
