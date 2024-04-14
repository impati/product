package com.example.productsearch.domain.product.repository

import com.example.productdomain.product.domain.ProductStatus
import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import com.example.productsearch.condition.NumberCondition
import com.example.productsearch.condition.TextCondition
import com.example.productsearch.condition.operator.DateOperator
import com.example.productsearch.condition.operator.NumberOperator
import com.example.productsearch.domain.common.condition.CreatedAuditCondition
import com.example.productsearch.domain.common.condition.UpdatedAuditCondition
import com.example.productsearch.domain.common.document.CreatedAuditDocument
import com.example.productsearch.domain.common.document.UpdatedAuditDocument
import com.example.productsearch.domain.product.condition.ProductCondition
import com.example.productsearch.domain.product.document.ProductDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ProductConditionElasticsearchRepositoryImplTest @Autowired constructor(
    val productDocumentRepository: ProductDocumentRepository
) {


    @Test
    fun findProductDocumentCase1() {
        indexDefaultProductDocument()
        val productCondition = ProductCondition(
            productId = NumberCondition(1, NumberOperator.EQUAL),
            name = TextCondition("hello"),
            createdAudit = CreatedAuditCondition(
                DateCondition.from(LocalDateTime.of(2024, 4, 9, 0, 0), DateOperator.AFTER),
                KeywordCondition("root")
            ),
            price = NumberCondition(1000, NumberOperator.MORE_THAN_OR_EQUAL),
            quantity = NumberCondition(100, NumberOperator.LESS_THAN_OR_EQUAL),
            status = KeywordCondition(ProductStatus.SELLING.name),
        )

        val documents = productDocumentRepository.findProductDocument(productCondition)

        assertThat(documents).hasSize(1)
    }

    @Test
    fun findProductDocumentCase2() {
        indexDefaultProductDocument()
        val productCondition = ProductCondition(
            updatedAudit = UpdatedAuditCondition(
                DateCondition.from(LocalDateTime.of(2024, 4, 11, 0, 0), DateOperator.BEFORE),
                KeywordCondition("root")
            ),
            name = TextCondition("hello world"),
            price = NumberCondition(10000, NumberOperator.LESS_THAN),
            quantity = NumberCondition(10, NumberOperator.MORE_THEN),
        )

        val documents = productDocumentRepository.findProductDocument(productCondition)

        assertThat(documents).hasSize(1)
    }

    @Test
    fun findProductDocumentCase3() {
        indexDefaultProductDocument()
        val productCondition = ProductCondition(
            updatedAudit = UpdatedAuditCondition(
                DateCondition.from(LocalDateTime.of(2024, 4, 11, 0, 0), DateOperator.AFTER),
                KeywordCondition("root")
            )
        )

        val documents = productDocumentRepository.findProductDocument(productCondition)

        assertThat(documents).isEmpty()
    }

    private fun indexDefaultProductDocument() {
        val document = ProductDocument(
            1L,
            "hello",
            CreatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
            UpdatedAuditDocument(LocalDateTime.of(2024, 4, 10, 0, 0), "root"),
            1000,
            100,
            ProductStatus.SELLING
        )
        productDocumentRepository.save(document)
    }

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
                ProductDocument::name,
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
