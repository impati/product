package com.example.productdomain.config

import com.example.productdomain.ProductDomainConfig
import com.example.productdomain.product.domain.ProductRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ProductDomainConfig::class])
class SpringBootTester {


    @Autowired
    lateinit var productRepository: ProductRepository


    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
    }
}
