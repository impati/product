package com.example.productdomain.product.domain

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.createDefaultProduct
import com.example.productdomain.product.exception.ProductOptimisticException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductTest {

    @Test
    @DisplayName("올바른 조건으로 상품을 생성한다.")
    fun create() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val product = Product(
            CreatedAudit(now, memberNumber),
            UpdatedAudit(now, memberNumber),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ImagePath("https://localhost")
        )


        assertThat(product)
            .extracting(Product::name, Product::price, Product::quantity, Product::status, Product::imagePath)
            .contains(
                ProductName("test"),
                ProductPrice(1000),
                ProductQuantity(100),
                ProductStatus.PRE_REGISTRATION,
                ImagePath("https://localhost")
            );
    }

    @Test
    @DisplayName("상품을 생성할 때 CreatedAudit,UpdatedAudit 정보를 함께 생성한다.")
    fun createAudit() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)


        val product = Product(
            CreatedAudit(now, memberNumber),
            UpdatedAudit(now, memberNumber),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ImagePath("https://localhost")
        )

        assertThat(product)
            .extracting(Product::createdAudit, Product::updatedAudit)
            .contains(CreatedAudit(now, memberNumber), UpdatedAudit(now, memberNumber))
    }

    @Test
    @DisplayName("상품 이름 , 가격 , 수량 , 상태를 변경할 수 있다.")
    fun update() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val product = createDefaultProduct()

        assertThat(product.apply {
            update(
                UpdatedAudit(now, memberNumber),
                "other",
                100000,
                10000,
                "https://localhost",
                ProductStatus.STOP
            )
        })
            .extracting(
                Product::name,
                Product::price,
                Product::quantity,
                Product::status,
                Product::createdAudit,
                Product::updatedAudit
            )
            .contains(
                ProductName("other"),
                ProductPrice(100000),
                ProductQuantity(10000),
                ProductStatus.STOP,
                CreatedAudit(LocalDateTime.of(2024, 3, 9, 0, 0), "default"),
                UpdatedAudit(now, memberNumber)
            )
    }

    @Test
    @DisplayName("상품의 이름이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfName() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)

        assertThatThrownBy {
            Product(
                CreatedAudit(now, memberNumber),
                UpdatedAudit(now, memberNumber),
                ProductName("test".repeat(500)),
                ProductPrice(1000),
                ProductQuantity(100),
                ImagePath("https://localhost")
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 이름은 1글자 이상 50글자 이하여야합니다.")
    }

    @Test
    @DisplayName("상품의 가격이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfPrice() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)

        assertThatThrownBy {
            Product(
                CreatedAudit(now, memberNumber),
                UpdatedAudit(now, memberNumber),
                ProductName("test"),
                ProductPrice(2_000_000_000),
                ProductQuantity(100),
                ImagePath("https://localhost")
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 가격은 0보다 크거나 같고 1000000000보다 작아야합니다.")
    }

    @Test
    @DisplayName("상품의 수량이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfQuantity() {
        val memberNumber = "root"
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)

        assertThatThrownBy {
            Product(
                CreatedAudit(now, memberNumber),
                UpdatedAudit(now, memberNumber),
                ProductName("test"),
                ProductPrice(1000),
                ProductQuantity(2_000_000_000),
                ImagePath("https://localhost")
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 수량은 0보다 크거나 같고 1000000000보다 작아야합니다.")
    }

    @Test
    @DisplayName("상품의 상태를 DELETE 로 변경한다.")
    fun delete() {
        val product = createDefaultProduct()
        val audit = UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")

        product.delete(audit)

        assertThat(product.status).isEqualTo(ProductStatus.DELETED)
        assertThat(product.updatedAudit).isEqualTo(UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"))
    }

    @Test
    @DisplayName("상품을 업데이트하는데 상품의 상태가 DELETE 라면 업데이트 할 수 없다.")
    fun updateFailWhenDelete() {
        val product = createDefaultProduct(ProductStatus.DELETED)
        val audit = UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root")

        assertThatThrownBy {
            product.update(
                audit,
                "other",
                100000,
                10000,
                "https://localhost",
                ProductStatus.STOP
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품이 이미 삭제되어 변경할 수 없습니다.")
    }

    @Test
    @DisplayName("상품의 버전이 맞지 않는 경우 ProductOptimisticException 를 throw 한다.")
    fun checkVersion() {
        val product = createDefaultProduct(ProductStatus.DELETED)

        assertThatThrownBy { product.checkVersion(2L) }
            .isInstanceOf(ProductOptimisticException::class.java)
            .hasMessage("수정에 실패했습니다. 다시 시도해주세요.")
    }
}
