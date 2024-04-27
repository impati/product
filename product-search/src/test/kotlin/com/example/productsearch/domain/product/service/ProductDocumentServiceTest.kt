package com.example.productsearch.domain.product.service

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.domain.*
import com.example.productdomain.product.event.ProductStatusEvent
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
import com.example.productsearch.domain.product.repository.ProductDocumentRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ProductDocumentServiceTest @Autowired constructor(
    val productDocumentRepository: ProductDocumentRepository
) {

    @BeforeEach
    fun setUp() {
        productDocumentRepository.save(
            ProductDocument(
                1L,
                "hello world",
                CreatedAuditDocument(LocalDateTime.of(2024, 4, 14, 0, 0), "impati"),
                UpdatedAuditDocument(LocalDateTime.of(2024, 4, 14, 0, 0), "impati"),
                100,
                100_000,
                ProductStatus.PRE_REGISTRATION
            )
        )
    }

    @AfterEach
    fun tearDown() {
        productDocumentRepository.deleteAll()
    }

    @Test
    fun indexing() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val product = Product(
            CreatedAudit(LocalDateTime.now(), "root"),
            UpdatedAudit(LocalDateTime.now(), "root"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ImagePath("https://localhost"),
            ProductStatus.PRE_REGISTRATION,
            99L
        )
        val event = ProductStatusEvent(product)

        productDocumentService.indexing(event)

        assertThat(productDocumentRepository.findById(product.id!!)).isPresent()
    }

    @Test
    fun searchCase0() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition()

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase1() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            productId = NumberCondition.eq(1)
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase2() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            name = TextCondition.contain("hello")
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase3() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            createdAudit = CreatedAuditCondition(
                DateCondition.ofBetween(
                    LocalDateTime.of(2024, 4, 11, 0, 0),
                    LocalDateTime.of(2024, 4, 15, 0, 0)
                ),
                null
            )
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase4() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            createdAudit = CreatedAuditCondition(
                null,
                KeywordCondition.eq("impati")
            )
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase5() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            price = NumberCondition.eq(100)
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase6() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            quantity = NumberCondition.operate(100, NumberOperator.MORE_THEN)
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase7() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            status = KeywordCondition.eq(ProductStatus.PRE_REGISTRATION.name)
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun searchCase8() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            updatedAudit = UpdatedAuditCondition(
                DateCondition.from(LocalDateTime.of(2024, 4, 15, 0, 0), DateOperator.BEFORE),
                KeywordCondition.eq("impati")
            )
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).hasSize(1)
            .extracting(ProductDocument::productId, ProductDocument::name)
            .contains(tuple(1L, "hello world"))
    }

    @Test
    fun noSearch() {
        val productDocumentService = ProductDocumentService(productDocumentRepository)
        val productCondition = ProductCondition(
            name = TextCondition.contain("helloworld")
        )

        val productDocuments = productDocumentService.search(productCondition)

        assertThat(productDocuments).isEmpty()
    }
}
