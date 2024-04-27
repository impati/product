package com.example.productsearch.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.elasticsearch.core.query.Criteria

class TextConditionTest {

    @Test
    fun textCondition() {
        val textCondition = TextCondition("hello")

        val criteria = textCondition.buildCriteria("textField")

        assertThat(criteria.queryCriteriaEntries)
            .isEqualTo(Criteria("textField").matches("hello").queryCriteriaEntries)
    }

    @Test
    fun contain() {
        val textCondition = TextCondition.contain("hello")

        assertThat(textCondition).isEqualTo(TextCondition("hello"))
    }

    @Test
    @DisplayName("contain 메서드의 인자가 null 이라면 TextCondition.contain() 은 null 을 반환한다.")
    fun containNull() {
        val textCondition = TextCondition.contain(null)

        assertThat(textCondition).isNull()
    }
}
