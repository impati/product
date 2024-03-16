package com.example.productdomain.product.application

import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductHistory
import com.example.productdomain.product.domain.ProductHistoryRepository
import com.example.productdomain.product.domain.ProductRepository
import com.example.productdomain.util.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional
class ProductCommandService(
    val productRepository: ProductRepository,
    val productHistoryRepository: ProductHistoryRepository
) {

    fun create(input: ProductCreateInput): Product {
        val product = productRepository.save(input.toProduct())
        productHistoryRepository.save(ProductHistory.from(product))

        return product;
    }

    fun edit(productId: Long, input: ProductEditInput, updatedAudit: UpdatedAudit): Product {
        val product: Product = productRepository.findByIdOrThrow(productId)

        product.update(updatedAudit, input.name, input.price, input.quantity, input.status)

        productHistoryRepository.save(ProductHistory.from(product))
        return product
    }

    fun delete(productId: Long, updatedAudit: UpdatedAudit) {
        val product: Product = productRepository.findByIdOrThrow(productId)

        product.delete(updatedAudit);

        productHistoryRepository.save(ProductHistory.from(product))
    }
}
