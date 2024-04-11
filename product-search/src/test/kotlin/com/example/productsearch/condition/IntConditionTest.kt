package com.example.productsearch.condition

import com.example.productsearch.condition.operator.NumberOperator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.data.elasticsearch.core.query.Criteria
import java.util.stream.Stream

class IntConditionTest {

    @ParameterizedTest
    @MethodSource("providerIntCondition")
    fun intCondition(numberOperator: NumberOperator, expectedCriteria: Criteria) {
        val defaultValue = getDefaultValue()
        val intCondition = IntCondition(defaultValue, numberOperator)

        val criteria = intCondition.buildCriteria("intField")

        assertThat(criteria.queryCriteriaEntries).isEqualTo(expectedCriteria.queryCriteriaEntries)
    }

    companion object {
        @JvmStatic
        fun providerIntCondition(): Stream<Arguments> {
            return Stream.of(
                arguments(NumberOperator.LESS_THAN, Criteria("intField").lessThan(getDefaultValue())),
                arguments(NumberOperator.MORE_THEN, Criteria("intField").greaterThan(getDefaultValue())),
                arguments(NumberOperator.EQUAL, Criteria("intField").`is`(getDefaultValue())),
                arguments(NumberOperator.LESS_THAN_OR_EQUAL, Criteria("intField").lessThanEqual(getDefaultValue())),
                arguments(NumberOperator.MORE_THAN_OR_EQUAL, Criteria("intField").greaterThanEqual(getDefaultValue())),
            )
        }

        fun getDefaultValue(): Int {
            return 10
        }
    }
}
