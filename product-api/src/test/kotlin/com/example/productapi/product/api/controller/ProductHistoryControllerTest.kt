package com.example.productapi.product.api.controller

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductHistoryQueryService
import com.example.productdomain.product.domain.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime

@ExtendWith(value = [RestDocumentationExtension::class])
@AutoConfigureRestDocs(uriHost = "api.test.com", uriPort = 8080)
@WebMvcTest(controllers = [ProductHistoryController::class])
class ProductHistoryControllerTest @Autowired constructor(

    @Autowired
    val mockMvc: MockMvc,

    @MockkBean
    val productHistoryQueryService: ProductHistoryQueryService
) {

    @Test
    @DisplayName("[GET] [/v1/products/{productId}/histories] 상품 히스토리 조회 API")
    fun getProductHistories() {
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val product = Product(
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(10),
            ImagePath("https://localhost"),
            ProductStatus.PRE_REGISTRATION,
            1L
        )

        every { productHistoryQueryService.getProductHistories(product.id!!) }
            .returns(listOf(ProductHistory.from(product)))

        mockMvc.perform(get("/v1/products/{productId}/histories", product.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.handler().methodName("getProductHistories"))
            .andExpect(jsonPath("$[0].name").value("test"))
            .andExpect(jsonPath("$[0].price").value("1000"))
            .andExpect(jsonPath("$[0].status").value("PRE_REGISTRATION"))
            .andExpect(jsonPath("$[0].createdBy").value("0000"))
            .andExpect(jsonPath("$[0].createdAt").value("2024-03-09T00:00:00"))
            .andDo(
                RestDocsUtils.prettyDocument(
                    "product/product-histories",
                    pathParameters(
                        parameterWithName("productId").description("상품 ID")
                    ),
                    responseFields(
                        fieldWithPath("[].name").description("상품 이름"),
                        fieldWithPath("[].price").description("상품 가격"),
                        fieldWithPath("[].status").description("상품 상태"),
                        fieldWithPath("[].createdAt").description("수정 일자"),
                        fieldWithPath("[].createdBy").description("수정자")
                    )
                )
            )

    }

}
