package com.example.productdomain.product.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    @DisplayName("올바른 조건으로 상품을 생성한다.")
    fun create() {
        val product = Product("test", 1000, 100)

        assertThat(product)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains("test", 1000, 100, ProductStatus.PRE_REGISTRATION);
    }

    @Test
    @DisplayName("상품의 이름이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfName() {
        assertThatThrownBy { Product("test".repeat(500), 1000, 100) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 이름은 1글자 이상 50글자 이하여야합니다.")
    }

    @Test
    @DisplayName("상품의 가격이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfPrice() {
        assertThatThrownBy { Product("test", 2_000_000_000, 100) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 가격은 0보다 크거나 같고 1000000000보다 작아야합니다.")
    }

    @Test
    @DisplayName("상품의 수량이 올바르지 않으면 상품 생성에 실패한다.")
    fun createFailBecauseOfQuantity() {
        assertThatThrownBy { Product("test", 1000, 2_000_000_000) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 수량은 0보다 크거나 같고 1000000000보다 작아야합니다.")
    }
}
