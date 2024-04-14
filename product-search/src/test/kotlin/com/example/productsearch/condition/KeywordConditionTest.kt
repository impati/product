package com.example.productsearch.condition


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
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

    @Test
    fun eq() {
        val keywordCondition = KeywordCondition.eq("hello")

        assertThat(keywordCondition).isEqualTo(KeywordCondition("hello"))
    }

    @Test
    @DisplayName("eq 메서드의 인자가 null 이라면 KeywordCondition.eq() 은 null 을 반환한다.")
    fun eqNull() {
        val keywordCondition = KeywordCondition.eq(null)

        assertThat(keywordCondition).isNull()
    }
}
