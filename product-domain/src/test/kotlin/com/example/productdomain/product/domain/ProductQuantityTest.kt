package com.example.productdomain.product.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductQuantityTest {
    @ParameterizedTest
    @DisplayName("정상적인 상품 수량으로 ProductQuantity 를 생성한다.")
    @ValueSource(ints = [0, 100, 1000, 10000, 1_000_000_000])
    fun create(value: Int) {
        val productQuantity = ProductQuantity(value)

        Assertions.assertThat(productQuantity.value).isEqualTo(value)
    }

    @ParameterizedTest
    @DisplayName("비정상적인 상품 수량으로 ProductQuantity 를 생성하면 실패한다.")
    @ValueSource(ints = [-1, -100, 1_000_000_001])
    fun createFail(value: Int) {
        Assertions.assertThatThrownBy { ProductQuantity(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 수량은 0보다 크거나 같고 1000000000보다 작아야합니다.")
    }
}
