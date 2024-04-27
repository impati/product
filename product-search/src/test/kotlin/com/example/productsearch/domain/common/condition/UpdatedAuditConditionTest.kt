package com.example.productsearch.domain.common.condition

import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import com.example.productsearch.condition.operator.DateOperator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.elasticsearch.core.query.Criteria
import java.time.LocalDateTime

class UpdatedAuditConditionTest {

    @Test
    fun updatedAuditCondition() {
        val dateCondition = DateCondition.from(
            LocalDateTime.of(2024, 4, 12, 0, 0),
            DateOperator.EQUAL
        )
        val keywordCondition = KeywordCondition("root")
        val auditCondition = UpdatedAuditCondition(dateCondition, keywordCondition)

        val buildCriteria = auditCondition.buildCriteria("field")

        val criteria = Criteria()
        criteria.and(dateCondition.buildCriteria("field.updatedAt"))
        criteria.and(keywordCondition.buildCriteria("field.updatedBy"))
        assertThat(buildCriteria.queryCriteriaEntries).isEqualTo(criteria.queryCriteriaEntries)
    }
}
