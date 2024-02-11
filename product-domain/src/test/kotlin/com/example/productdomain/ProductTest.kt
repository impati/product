package com.example.productdomain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun test() {
        val product = Product("ice")

        product.changeName("ice cream")

        assertThat(product.name).isEqualTo("ice cream")
    }
}
