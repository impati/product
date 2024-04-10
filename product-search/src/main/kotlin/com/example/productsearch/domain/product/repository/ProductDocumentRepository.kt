package com.example.productsearch.domain.product.repository

import com.example.productsearch.domain.product.document.ProductDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ProductDocumentRepository : ElasticsearchRepository<ProductDocument, Long> {
}
