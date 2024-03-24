package com.example.productdomain.product.application

import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.*
import com.example.productdomain.product.exception.ProductOptimisticException
import com.example.productdomain.util.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional
class ProductCommandService(
    val productRepository: ProductRepository,
    val productHistoryRepository: ProductHistoryRepository,
    val productEditor: ProductEditor
) {

    fun create(input: ProductCreateInput): Product {
        val product = productRepository.save(input.toProduct())

        saveProductHistory(product)
        return product;
    }

    fun edit(productId: Long, input: ProductEditInput, updatedAudit: UpdatedAudit): Product {
        try {
            val product = productEditor.doEdit(
                productId,
                updatedAudit,
                input.name,
                input.price,
                input.quantity,
                input.status,
                input.version
            )
            saveProductHistory(product)
            return product
        } catch (e: OptimisticLockingFailureException) {
            throw ProductOptimisticException("수정에 실패했습니다. 다시 시도해주세요.")
        }
    }

    fun delete(productId: Long, updatedAudit: UpdatedAudit) {
        val product: Product = productRepository.findByIdOrThrow(productId)

        product.delete(updatedAudit);

        saveProductHistory(product)
    }

    private fun saveProductHistory(product: Product) {
        productHistoryRepository.save(ProductHistory.from(product))
    }
}
