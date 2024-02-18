package com.example.productdomain.product.application

import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductRepository

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
