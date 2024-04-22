package com.example.productsearch

import com.example.productsearch.domain.product.document.ProductDocument
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Component


@Component
class ProductIndexInitializer(
    val elasticsearchOperations: ElasticsearchOperations
) {

    @EventListener(ApplicationReadyEvent::class)
    fun createIndexIfNotExists() {
        val indexOperations = elasticsearchOperations.indexOps(ProductDocument::class.java)

        if (!indexOperations.exists()) {
            indexOperations.create()
            indexOperations.putMapping()
        }
    }
}
