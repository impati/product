package com.example.productsearch.domain.product.service

import com.example.productdomain.product.event.ProductStatusEvent
import com.example.productsearch.domain.product.condition.ProductCondition
import com.example.productsearch.domain.product.document.ProductDocument
import com.example.productsearch.domain.product.repository.ProductDocumentRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class ProductDocumentService(
    val productDocumentRepository: ProductDocumentRepository
) {
    @Async
    @EventListener
    fun indexing(event: ProductStatusEvent) {
        productDocumentRepository.save(ProductDocument.from(event.product))
    }

    fun search(productCondition: ProductCondition): List<ProductDocument> {
        return productDocumentRepository.findProductDocument(productCondition)
    }
}
