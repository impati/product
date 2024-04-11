package com.example.productsearch.condition

import com.example.productsearch.condition.operator.DateOperator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.data.elasticsearch.core.query.Criteria
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Stream

class DateConditionTest {

    @ParameterizedTest
    @MethodSource("providerDateCondition")
    fun from(dateOperator: DateOperator, expectedCriteria: Criteria) {
        val dateCondition = DateCondition.from(defaultLocalDateTime(), dateOperator)

        val criteria = dateCondition.buildCriteria("dateFieldName")

        assertThat(criteria.queryCriteriaEntries).isEqualTo(expectedCriteria.queryCriteriaEntries)
    }

    @Test
    fun ofBetween() {
        val dateCondition = DateCondition.ofBetween(defaultLocalDateTime(), defaultLocalDateTime())

        val criteria = dateCondition.buildCriteria("dateFieldName")

        Criteria("dateFieldName").between(
            defaultLocalDateTimeFormat(defaultLocalDateTime()),
            defaultLocalDateTimeFormat(defaultLocalDateTime())
        )
            .queryCriteriaEntries.stream()
            .forEach { assertThat(criteria.queryCriteriaEntries.contains(it)) }
    }

    companion object {
        @JvmStatic
        fun providerDateCondition(): Stream<Arguments> {
            return Stream.of(
                arguments(
                    DateOperator.AFTER,
                    Criteria("dateFieldName").greaterThan(defaultLocalDateTimeFormat(defaultLocalDateTime()))
                ),
                arguments(
                    DateOperator.AFTER_OR_EQUAL,
                    Criteria("dateFieldName").greaterThanEqual(defaultLocalDateTimeFormat(defaultLocalDateTime()))
                ),
                arguments(
                    DateOperator.BEFORE,
                    Criteria("dateFieldName").lessThan(defaultLocalDateTimeFormat(defaultLocalDateTime()))
                ),
                arguments(
                    DateOperator.BEFORE_OR_EQUAL,
                    Criteria("dateFieldName").lessThanEqual(defaultLocalDateTimeFormat(defaultLocalDateTime()))
                ),
                arguments(
                    DateOperator.EQUAL,
                    Criteria("dateFieldName").`is`(defaultLocalDateTimeFormat(defaultLocalDateTime()))
                )
            )
        }

        private fun defaultLocalDateTimeFormat(at: LocalDateTime): String {
            return at.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        private fun defaultLocalDateTime(): LocalDateTime {
            return LocalDateTime.of(2024, 4, 10, 0, 0)
        }
    }
}
