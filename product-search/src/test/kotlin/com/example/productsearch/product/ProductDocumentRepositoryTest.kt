package com.example.productsearch.product

import com.example.productdomain.product.domain.ProductStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ProductDocumentRepositoryTest @Autowired constructor(
    val productDocumentRepository: ProductDocumentRepository
) {

    @Test
    fun index() {
        val document = ProductDocument(
            1L,
            "hello",
            CreatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
            UpdatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
            1000,
            100,
            ProductStatus.SELLING
        )

        val indexedDocument = productDocumentRepository.save(document)

        assertThat(indexedDocument)
            .extracting(
                ProductDocument::productId,
                ProductDocument::productName,
                ProductDocument::createdAudit,
                ProductDocument::updatedAudit,
                ProductDocument::price,
                ProductDocument::quantity,
                ProductDocument::status,
            )
            .contains(
                1L,
                "hello",
                CreatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
                UpdatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
                1000,
                100,
                ProductStatus.SELLING
            )
    }
}
