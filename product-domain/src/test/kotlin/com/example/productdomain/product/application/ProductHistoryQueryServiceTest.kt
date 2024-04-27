package com.example.productdomain.product.application

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.config.SpringBootTester
import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.domain.ProductStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class ProductHistoryQueryServiceTest @Autowired constructor(
    val productCommandService: ProductCommandService,
    val productHistoryQueryService: ProductHistoryQueryService
) : SpringBootTester() {

    @Test
    @DisplayName("상품의 이력을 조회한다.")
    fun getProductHistories() {
        val now = LocalDateTime.of(2024, 2, 16, 0, 0)
        val createInput = ProductCreateInput(
            "test1",
            100,
            10,
            "https://image",
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000")
        )
        val editInput = ProductEditInput("test2", 1000, 100, ProductStatus.SELLING, "https://localhost", 0)
        val product = productCommandService.create(createInput)
        productCommandService.edit(product.id!!, editInput, UpdatedAudit(now.plusDays(1), "0000"))
        productCommandService.delete(product.id!!, UpdatedAudit(now.plusDays(2), "0000"))

        val productHistories = productHistoryQueryService.getProductHistories(product.id!!)

        assertThat(productHistories).hasSize(3)
    }
}
