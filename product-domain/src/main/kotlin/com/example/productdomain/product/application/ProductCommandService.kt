package com.example.productdomain.product.application

import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductRepository
import jakarta.transaction.Transactional
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@Transactional
class ProductCommandService(
    val productRepository: ProductRepository
) {

    fun create(input: ProductCreateInput): Product {
        return productRepository.save(input.toProduct());
    }
}
