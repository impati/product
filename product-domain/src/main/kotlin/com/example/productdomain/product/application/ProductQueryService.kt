package com.example.productdomain.product.application


import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductRepository
import com.example.productdomain.util.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional(readOnly = true)
class ProductQueryService(
    val productRepository: ProductRepository
) {

    fun getProduct(productId: Long): Product {
        return productRepository.findByIdOrThrow(productId)
    }
}
