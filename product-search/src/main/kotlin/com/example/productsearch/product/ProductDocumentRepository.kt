package com.example.productsearch.product

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ProductDocumentRepository : ElasticsearchRepository<ProductDocument, Long> {
}
