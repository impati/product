package com.example.productdomain.product.application

import com.example.productdomain.config.SpringBootTester
import com.example.productdomain.product.createDefaultProduct
import com.example.productdomain.product.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductQueryServiceTest @Autowired constructor(
    val productQueryService: ProductQueryService
) : SpringBootTester() {

    @Test
    @DisplayName("상품 ID 로 상품을 조회한다.")
    fun getProduct() {
        val product = Product(ProductName("Test"), ProductPrice(100), ProductQuantity(10))
        val persistentProduct = productRepository.save(product)

        val foundProduct = productQueryService.getProduct(persistentProduct.id!!)

        assertThat(foundProduct)
            .extracting(Product::id, Product::name, Product::price, Product::quantity, Product::status)
            .contains(
                persistentProduct.id,
                ProductName("Test"),
                ProductPrice(100),
                ProductQuantity(10),
                ProductStatus.PRE_REGISTRATION
            )
    }

    @Test
    @DisplayName("상품 ID 에 해당하는 상품이 없으면 조회하는데 실패한다.")
    fun failGetProduct() {
        val product = createDefaultProduct()
        val persistentProduct = productRepository.save(product)

        assertThatThrownBy { productQueryService.getProduct(-persistentProduct.id!!) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("찾고자하는 엔티티를 찾지 못했습니다.")
    }
}
