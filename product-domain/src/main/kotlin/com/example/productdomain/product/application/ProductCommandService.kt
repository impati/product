package com.example.productdomain.product.application

import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductRepository
import com.example.productdomain.util.findByIdOrThrow
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
        val product = productRepository.save(input.toProduct())

        return product;
    }

    fun edit(productId: Long, input: ProductEditInput): Product {
        val product: Product = productRepository.findByIdOrThrow(productId)

        return product.apply { update(input.name, input.price, input.quantity, input.status) }
    }

    fun delete(productId: Long) {
        val product: Product = productRepository.findByIdOrThrow(productId)

        product.delete();
    }
}
