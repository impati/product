package com.example.productdomain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun changeName() {
        val product = Product("ice")

        product.changeName("ice cream")

        assertThat(product.name).isEqualTo("ice cream")
    }

    @Test
    fun changeAge() {
        val product = Product("ice", 25)

        product.changeAge(26)

        assertThat(product.age).isEqualTo(26)
    }

    @Test
    fun fail() {
        val product = Product("ice")

        product.changeName("ice cream")

        assertThat(product.name).isEqualTo("ice")
    }
}
