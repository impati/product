package com.example.productdomain.product.domain

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.createDefaultProduct
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductHistoryTest {

    @Test
    @DisplayName("상품 생성시 생성에 대한 히스토리를 생성한다.")
    fun createAtHistory() {
        val product = createDefaultProduct()

        val productHistory = ProductHistory.from(product)

        assertThat(productHistory)
            .extracting(
                ProductHistory::createdAudit,
                ProductHistory::name,
                ProductHistory::price,
                ProductHistory::status,
                ProductHistory::productId,
                ProductHistory::imagePath
            )
            .contains(
                CreatedAudit(product.updatedAudit.updatedAt, product.updatedAudit.updatedBy),
                product.name.value,
                product.price.value,
                product.status,
                product.id,
                product.imagePath.value
            )
    }

    @Test
    @DisplayName("상품 수정시 생성에 대한 히스토리를 생성한다.")
    fun updateAtHistory() {
        val product = createDefaultProduct()
        product.update(
            UpdatedAudit(LocalDateTime.of(2024, 3, 16, 0, 0), "root"),
            "new",
            1000000,
            1000000,
            "https://localhost",
            ProductStatus.SELLING
        )

        val productHistory = ProductHistory.from(product)

        assertThat(productHistory)
            .extracting(
                ProductHistory::createdAudit,
                ProductHistory::name,
                ProductHistory::price,
                ProductHistory::status,
                ProductHistory::productId,
                ProductHistory::imagePath
            )
            .contains(
                CreatedAudit(LocalDateTime.of(2024, 3, 16, 0, 0), "root"),
                "new",
                1000000,
                1000000,
                ProductStatus.SELLING,
                product.id,
                "https://localhost"
            )
    }

    @Test
    @DisplayName("상품 삭제시 생성에 대한 히스토리를 생성한다.")
    fun deleteAtHistory() {
        val product = createDefaultProduct()
        product.delete(UpdatedAudit(LocalDateTime.of(2024, 3, 15, 0, 0), "root"))

        val productHistory = ProductHistory.from(product)

        assertThat(productHistory)
            .extracting(
                ProductHistory::createdAudit,
                ProductHistory::name,
                ProductHistory::price,
                ProductHistory::status,
                ProductHistory::productId
            )
            .contains(
                CreatedAudit(LocalDateTime.of(2024, 3, 15, 0, 0), "root"),
                "test",
                1000,
                ProductStatus.DELETED,
                product.id
            )
    }
}


