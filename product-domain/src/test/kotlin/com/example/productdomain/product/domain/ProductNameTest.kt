package com.example.productdomain.product.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductNameTest {

    @Test
    @DisplayName("길이가 1인 정상적인 상품 이름으로 ProductName 를 생성한다.")
    fun createWhenMinLength() {
        val productName = ProductName("a")

        assertThat(productName.value).isEqualTo("a");
    }

    @Test
    @DisplayName("길이가 50인 정상적인 상품 이름으로 ProductName 를 생성한다.")
    fun createWhenMaxLength() {
        val name = "a".repeat(50)

        val productName = ProductName(name)

        assertThat(productName.value).isEqualTo(name);
    }

    @Test
    @DisplayName("길이가 0인 비정상적인 이름 길이로  ProductName 를 생성하면 실패한다.")
    fun createFail() {
        val name = ""

        assertThatThrownBy { ProductName(name) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 이름은 1글자 이상 50글자 이하여야합니다.")
    }

    @Test
    @DisplayName("길이가 50보다 큰 비정상적인 이름 길이로  ProductName 를 생성하면 실패한다.")
    fun createFailBecauseOfThan50() {
        val name = "a".repeat(51)

        assertThatThrownBy { ProductName(name) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 이름은 1글자 이상 50글자 이하여야합니다.")
    }
}
