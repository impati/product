package com.example.productdomain.product.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ProductHistoryRepository : JpaRepository<ProductHistory, Long> {
    fun findByProductId(productId: Long): List<ProductHistory>
}
