package com.example.productsearch.condition


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.elasticsearch.core.query.Criteria

class KeywordConditionTest {

    @Test
    fun keywordCondition() {
        val keywordCondition = KeywordCondition("hello")

        val criteria = keywordCondition.buildCriteria("keywordField")

        assertThat(criteria.queryCriteriaEntries)
            .isEqualTo(Criteria("keywordField").`is`("hello").queryCriteriaEntries)
    }
}
