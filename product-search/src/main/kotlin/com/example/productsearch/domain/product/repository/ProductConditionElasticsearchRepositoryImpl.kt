package com.example.productsearch.domain.product.repository

import com.example.productsearch.domain.product.condition.ProductCondition
import com.example.productsearch.domain.product.document.ProductDocument
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.search
import org.springframework.stereotype.Repository

@Repository
class ProductConditionElasticsearchRepositoryImpl(
    val elasticsearchOperations: ElasticsearchOperations
) : ProductConditionElasticsearchRepository {
    override fun findProductDocument(productCondition: ProductCondition): List<ProductDocument> {
        val criteria = productCondition.buildCriteria()

        return elasticsearchOperations.search<ProductDocument>(CriteriaQuery(criteria)).stream()
            .map { it.content }
            .toList();
    }
}
