package com.example.productapi.product.application

import com.example.productapi.SpringBootTester
import com.example.productapi.product.api.request.*
import com.example.productapi.product.api.response.ProductDetailResponse
import com.example.productapi.product.api.response.ProductResponse
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.ProductStatus
import com.example.productsearch.condition.operator.NumberOperator
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ProductFacadeTest @Autowired constructor(
    val productFacade: ProductFacade
) : SpringBootTester() {

    @Test
    @DisplayName("상품 생성 권한이 있는 memberNumber 인 경우 상품 생성할 수 있다.")
    fun createProduct() {
        val memberNumber = "0000"
        val createdAudit = CreatedAudit(LocalDateTime.of(2024, 3, 9, 0, 0), memberNumber)
        val request = ProductCreateRequest("test", 1000, 1000, "0000", "https://image")

        val productId = productFacade.createProduct(request, createdAudit)

        assertThat(productId).isNotNull()
    }

    @Test
    @DisplayName("상품 생성 권한이 없는 memberNumber 인 경우 상품 생성할 수 없다.")
    fun createProductFail() {
        val memberNumber = "9999"
        val createdAudit = CreatedAudit(LocalDateTime.of(2024, 3, 9, 0, 0), memberNumber)
        val request = ProductCreateRequest("test", 1000, 1000, "0000", "https://image")

        assertThatThrownBy { productFacade.createProduct(request, createdAudit) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("사용자가 상품 생성 권한을 가지고 있지 않습니다: 멤버번호 $memberNumber")
    }

    @Test
    @DisplayName("상품 수정 권한이 있는 memberNumber 인 경우 상품을 수정할 수 있다.")
    fun editProduct() {
        val memberNumber = "0000"
        val createdAt = LocalDateTime.of(2024, 3, 9, 0, 0)
        val productId = productFacade.createProduct(
            ProductCreateRequest("test", 1000, 1000, "0000", "https://image"),
            CreatedAudit(createdAt, memberNumber)
        )
        val updateAudit = UpdatedAudit(createdAt.plusDays(1), memberNumber)
        val request = ProductEditRequest(
            "test2",
            10,
            1,
            ProductStatus.SELLING,
            "0000",
            "https://image",
            0
        )

        val response = productFacade.editProduct(productId, request, updateAudit)

        assertThat(response)
            .extracting(
                ProductResponse::productId,
                ProductResponse::name,
                ProductResponse::price,
                ProductResponse::quantity,
                ProductResponse::status
            )
            .contains(
                productId,
                "test2",
                10,
                1,
                ProductStatus.SELLING
            )
    }

    @Test
    @DisplayName("상품 수정 권한이 없는 memberNumber 인 경우 상품 수정할 수 없다.")
    fun editProductFail() {
        val memberNumber = "9998"
        val updateAudit = UpdatedAudit(LocalDateTime.of(2024, 3, 9, 0, 0), memberNumber)
        val request = ProductEditRequest("test2", 10, 1, ProductStatus.SELLING, memberNumber, "https://localhost", 0)

        assertThatThrownBy { productFacade.editProduct(1L, request, updateAudit) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("사용자가 상품 수정 권한을 가지고 있지 않습니다: 멤버번호 $memberNumber")
    }

    @Test
    @DisplayName("상품 삭제 권한이 있는 memberNumber 인 경우 상품을 삭제할 수 있다.")
    fun deleteProduct() {
        val memberNumber = "0000"
        val createdAt = LocalDateTime.of(2024, 3, 9, 0, 0)
        val productId = productFacade.createProduct(
            ProductCreateRequest("test", 1000, 1000, "0000", "https://image"),
            CreatedAudit(createdAt, memberNumber)
        )
        val updateAudit = UpdatedAudit(createdAt.plusDays(1), memberNumber)

        assertThatCode { productFacade.deleteProduct(productId, updateAudit) }
            .doesNotThrowAnyException()
    }

    @Test
    @DisplayName("상품 삭제 권한이 없는 memberNumber 인 경우 상품 삭제할 수 없다.")
    fun deleteProductFail() {
        val memberNumber = "9997"
        val updateAudit = UpdatedAudit(LocalDateTime.of(2024, 3, 9, 0, 0), memberNumber)

        assertThatThrownBy { productFacade.deleteProduct(1L, updateAudit) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("사용자가 상품 삭제 권한을 가지고 있지 않습니다: 멤버번호 $memberNumber")
    }

    @Test
    fun searchProduct() {
        val memberNumber = "0000"
        val createdAt = LocalDateTime.of(2024, 3, 9, 0, 0)
        val productId = productFacade.createProduct(
            ProductCreateRequest("test", 1000, 10000, "0000", "https://image"),
            CreatedAudit(createdAt, memberNumber)
        )
        val request = ProductSearchRequest(
            createdBy = memberNumber,
            productPrice = NumberOperateRequest(1000, NumberOperator.EQUAL),
            productCreatedTime = BetweenDateTimeRequest(
                LocalDateTime.of(2024, 3, 1, 0, 0),
                LocalDateTime.of(2024, 4, 14, 0, 0)
            )
        )
        val response = productFacade.searchProduct(request)

        assertThat(response.products).hasSize(1)
            .extracting(
                ProductDetailResponse::productId,
                ProductDetailResponse::name,
                ProductDetailResponse::price,
                ProductDetailResponse::quantity,
                ProductDetailResponse::status,
                ProductDetailResponse::createdBy,
                ProductDetailResponse::createdAt,
                ProductDetailResponse::updatedBy,
                ProductDetailResponse::updatedAt,
            )
            .contains(
                tuple(
                    productId,
                    "test",
                    1000,
                    10000,
                    ProductStatus.PRE_REGISTRATION,
                    memberNumber,
                    createdAt,
                    memberNumber,
                    createdAt
                )
            )
    }

    @Test
    fun searchProductFail() {
        val memberNumber = "0000"
        val createdAt = LocalDateTime.of(2024, 3, 9, 0, 0)
        productFacade.createProduct(
            ProductCreateRequest("test", 1000, 1000, "0000", "https://image"),
            CreatedAudit(createdAt, memberNumber)
        )
        val request = ProductSearchRequest(
            createdBy = memberNumber,
            productPrice = NumberOperateRequest(100, NumberOperator.EQUAL),
            productCreatedTime = BetweenDateTimeRequest(
                LocalDateTime.of(2024, 4, 1, 0, 0),
                LocalDateTime.of(2024, 4, 14, 0, 0)
            )
        )
        Thread.sleep(1000)

        val response = productFacade.searchProduct(request)

        assertThat(response.products).isEmpty()
    }
}
