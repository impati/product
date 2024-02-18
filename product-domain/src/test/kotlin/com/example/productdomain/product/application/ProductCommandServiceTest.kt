package com.example.productdomain.product.application

import com.example.productdomain.config.SpringBootTester
import com.example.productdomain.product.application.dto.ProductCreateInput
import com.example.productdomain.product.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductCommandServiceTest(
    @Autowired
    val productCommandService: ProductCommandService
) : SpringBootTester() {

    @Test
    @DisplayName("상품 기본 정보로 상품을 생성한다.")
    fun create() {
        val input = ProductCreateInput("test", 1000, 10)

        val product = productCommandService.create(input)

        assertThat(product.id).isNotNull()
        assertThat(product)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains(ProductName("test"), ProductPrice(1000), ProductQuantity(10), ProductStatus.PRE_REGISTRATION);
    }
}
