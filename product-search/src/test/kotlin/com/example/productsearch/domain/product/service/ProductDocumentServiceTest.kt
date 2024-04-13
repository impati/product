package com.example.productsearch.domain.product.service

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.*
import com.example.productdomain.product.event.ProductStatusEvent
import com.example.productsearch.domain.product.repository.ProductDocumentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ProductDocumentServiceTest @Autowired constructor(
    val productDocumentRepository: ProductDocumentRepository
) {

    @Test
    fun indexing() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val product = Product(
            CreatedAudit(LocalDateTime.now(), "root"),
            UpdatedAudit(LocalDateTime.now(), "root"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ProductStatus.PRE_REGISTRATION,
            0L
        )
        val event = ProductStatusEvent(product)

        productDocumentService.indexing(event)

        assertThat(productDocumentRepository.findById(product.id!!)).isPresent()
    }
}
