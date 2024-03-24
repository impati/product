package com.example.productdomain.product.domain

import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.exception.ProductOptimisticException
import com.example.productdomain.util.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Component
class ProductEditor(
    val productRepository: ProductRepository,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun doEdit(
        productId: Long,
        updatedAudit: UpdatedAudit,
        name: String,
        price: Int,
        quantity: Int,
        status: ProductStatus,
        version: Long
    ): Product {
        val product: Product = productRepository.findByIdOrThrow(productId)

        if (product.version != version) {
            throw ProductOptimisticException("수정에 실패했습니다. 다시 시도해주세요.")
        }

        return product.apply { update(updatedAudit, name, price, quantity, status) }
    }
}
