package com.example.productapi

import com.example.productdomain.product.domain.ProductHistoryRepository
import com.example.productdomain.product.domain.ProductRepository
import com.example.productsearch.domain.product.repository.ProductDocumentRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SpringBootTester {


    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productHistoryRepository: ProductHistoryRepository

    @Autowired
    lateinit var productDocumentRepository: ProductDocumentRepository

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
        productHistoryRepository.deleteAll()
        productDocumentRepository.deleteAll()
    }
}
