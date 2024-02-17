package com.example.productdomain.product.domain

import com.example.productdomain.ProductDomainConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ProductDomainConfig::class])
class ProductRepositoryTest(
    @Autowired
    val productRepository: ProductRepository
) {

    @Test
    @DisplayName("product 를 저장소에 저장하는 기본 테스트")
    fun save() {
        val product = Product(ProductName("test"), ProductPrice(1000), ProductQuantity(100))

        val persistProduct = productRepository.save(product)

        assertThat(persistProduct.id).isNotNull()
        assertThat(persistProduct)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains(ProductName("test"), ProductPrice(1000), ProductQuantity(100), ProductStatus.PRE_REGISTRATION)
    }
}
