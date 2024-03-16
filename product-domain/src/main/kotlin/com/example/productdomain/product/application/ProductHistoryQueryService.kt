package com.example.productdomain.product.application

import com.example.productdomain.product.domain.ProductHistory
import com.example.productdomain.product.domain.ProductHistoryRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional(readOnly = true)
class ProductHistoryQueryService(
    val productHistoryRepository: ProductHistoryRepository
) {

    fun getProductHistories(productId: Long): List<ProductHistory> {

        return productHistoryRepository.findByProductId(productId)
    }
}
