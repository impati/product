package com.example.productsearch.domain.product.repository

import com.example.productsearch.domain.product.condition.ProductCondition
import com.example.productsearch.domain.product.document.ProductDocument

interface ProductConditionElasticsearchRepository {

    fun findProductDocument(productCondition: ProductCondition): List<ProductDocument>
}
