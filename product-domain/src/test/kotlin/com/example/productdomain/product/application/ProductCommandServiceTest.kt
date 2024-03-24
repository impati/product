package com.example.productdomain.product.application

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.config.SpringBootTester
import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.application.dto.ProductEditInput
import com.example.productdomain.product.createDefaultProduct
import com.example.productdomain.product.domain.*
import com.example.productdomain.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ProductCommandServiceTest @Autowired constructor(
    val productCommandService: ProductCommandService
) : SpringBootTester() {

    @Test
    @DisplayName("상품 기본 정보로 상품을 생성한다.")
    fun create() {
        val now = LocalDateTime.of(2024, 3, 6, 0, 0)
        val input = ProductCreateInput("test", 1000, 10, CreatedAudit(now, "0000"), UpdatedAudit(now, "0000"))

        val product = productCommandService.create(input)

        assertThat(product.id).isNotNull()
        assertThat(product)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains(ProductName("test"), ProductPrice(1000), ProductQuantity(10), ProductStatus.PRE_REGISTRATION);
    }

    @Test
    @DisplayName("상품 기본 정보로 상품을 생성시 히스토리도 생성한다.")
    fun createHistory() {
        val now = LocalDateTime.of(2024, 3, 6, 0, 0)
        val input = ProductCreateInput("test", 1000, 10, CreatedAudit(now, "0000"), UpdatedAudit(now, "0000"))

        val product = productCommandService.create(input)

        val history = productHistoryRepository.findByProductId(product.id!!)[0]
        assertThat(history.id).isNotNull()
        assertThat(history)
            .extracting(
                ProductHistory::name,
                ProductHistory::price,
                ProductHistory::status,
                ProductHistory::createdAudit
            )
            .contains("test", 1000, ProductStatus.PRE_REGISTRATION, CreatedAudit(now, "0000"));
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    fun edit() {
        val beforeProduct = productRepository.save(createDefaultProduct())
        val input = ProductEditInput("test2", 100, 10000, ProductStatus.SELLING, 0)

        productCommandService.edit(
            beforeProduct.id!!,
            input,
            UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")
        );

        val afterProduct = productRepository.findByIdOrThrow(beforeProduct.id)
        assertThat(afterProduct.id).isEqualTo(beforeProduct.id)
        assertThat(afterProduct)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains(ProductName("test2"), ProductPrice(100), ProductQuantity(10000), ProductStatus.SELLING);
    }

    @Test
    @DisplayName("상품을 동시에 수정하면 첫 번째 수정만 반영되고 이후 수정은 실패한다.")
    fun editConcurrency() {
        val beforeProduct = productRepository.save(createDefaultProduct())

        val threads = List(3) { it ->
            thread(start = true) {
                productCommandService.edit(
                    beforeProduct.id!!,
                    ProductEditInput("test$it", 100, 10000, ProductStatus.SELLING, 0),
                    UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")
                )
            }
        }
        threads.forEach { it.join() }


        val afterProduct = productRepository.findByIdOrThrow(beforeProduct.id)
        assertThat(afterProduct.version).isEqualTo(1L)
    }

    @Test
    @DisplayName("상품 수정시 히스토리도 생성한다.")
    fun editHistory() {
        val beforeProduct = productRepository.save(createDefaultProduct())
        val input = ProductEditInput("test2", 100, 10000, ProductStatus.SELLING, 0)

        val afterProduct = productCommandService.edit(
            beforeProduct.id!!,
            input,
            UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")
        );

        val history = productHistoryRepository.findByProductId(afterProduct.id!!)[0]
        assertThat(history.id).isNotNull()
        assertThat(history)
            .extracting(
                ProductHistory::name,
                ProductHistory::price,
                ProductHistory::status,
                ProductHistory::createdAudit
            )
            .contains(
                "test2",
                100,
                ProductStatus.SELLING,
                CreatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")
            )
    }

    @Test
    @DisplayName("상품 ID 에 해당하는 상품을 삭제한다.")
    fun delete() {
        val product = createDefaultProduct()
        val persistProduct = productRepository.save(product)

        productCommandService.delete(persistProduct.id!!, UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"))

        assertThatThrownBy { productRepository.findByIdOrThrow(persistProduct.id) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("찾고자하는 엔티티를 찾지 못했습니다.")
    }

    @Test
    @DisplayName("상품 ID 에 해당하는 상품을 삭제한다.")
    fun deleteHistory() {
        val product = createDefaultProduct()
        val persistProduct = productRepository.save(product)

        productCommandService.delete(persistProduct.id!!, UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"))

        val history = productHistoryRepository.findByProductId(product.id!!)[0]
        assertThat(history.id).isNotNull()
        assertThat(history)
            .extracting(
                ProductHistory::status,
                ProductHistory::createdAudit
            )
            .contains(
                ProductStatus.DELETED,
                CreatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")
            )
    }
}
