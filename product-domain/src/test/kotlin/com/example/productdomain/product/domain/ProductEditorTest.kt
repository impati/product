package com.example.productdomain.product.domain

import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.config.SpringBootTester
import com.example.productdomain.product.createDefaultProduct
import com.example.productdomain.product.exception.ProductOptimisticException
import com.example.productdomain.util.findByIdOrThrow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class ProductEditorTest @Autowired constructor(
    val productEditor: ProductEditor
) : SpringBootTester() {

    @Test
    @DisplayName("상품 수정한다.")
    fun doEdit() {
        val beforeProduct = productRepository.save(createDefaultProduct())

        productEditor.doEdit(
            beforeProduct.id!!,
            UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"),
            "test2",
            100,
            100,
            ProductStatus.SOLD_OUT,
            0L
        )

        val afterProduct = productRepository.findByIdOrThrow(beforeProduct.id)
        Assertions.assertThat(afterProduct.id).isEqualTo(beforeProduct.id)
        Assertions.assertThat(afterProduct)
            .extracting(Product::name, Product::price, Product::quantity, Product::status)
            .contains(ProductName("test2"), ProductPrice(100), ProductQuantity(100), ProductStatus.SOLD_OUT)
    }

    @Test
    @DisplayName("상품을 수정하는데 버전이 맞지 않으면 실패한다.")
    fun doEditFail() {
        val beforeProduct = productRepository.save(createDefaultProduct())

        productEditor.doEdit(
            beforeProduct.id!!,
            UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"),
            "test2",
            100,
            100,
            ProductStatus.SOLD_OUT,
            0L
        )

        assertThatThrownBy {
            productEditor.doEdit(
                beforeProduct.id!!,
                UpdatedAudit(LocalDateTime.of(2023, 12, 31, 0, 0), "root"),
                "test2",
                100,
                100,
                ProductStatus.SOLD_OUT,
                0L
            )
        }
            .isInstanceOf(ProductOptimisticException::class.java)
            .hasMessage("수정에 실패했습니다. 다시 시도해주세요.")
    }
}
