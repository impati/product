package com.example.productsearch.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.elasticsearch.core.query.Criteria

class TextConditionTest {

    @Test
    fun textCondition() {
        val textCondition = TextCondition("hello")

        val criteria = textCondition.buildCriteria("textField")

        assertThat(criteria.queryCriteriaEntries)
            .isEqualTo(Criteria("textField").contains("hello").queryCriteriaEntries)
    }
}
