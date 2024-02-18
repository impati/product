package com.example.productapi.product.controller

import com.example.productapi.product.controller.request.ProductCreateRequest
import com.example.productdomain.product.application.ProductCommandService
import com.example.productdomain.product.domain.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(value = [RestDocumentationExtension::class])
@AutoConfigureRestDocs(uriHost = "api.test.com", uriPort = 8080)
@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest @Autowired constructor(
    @Autowired
    val mockMvc: MockMvc,
    @MockBean
    val productCommandService: ProductCommandService,
    @Autowired
    val objectMapper: ObjectMapper
) {

    @Test
    @DisplayName("[POST] [/v1/products] 상품 생성 API")
    fun createProduct() {
        val request = ProductCreateRequest("test", 1000, 10)
        val product = Product(
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(10),
            ProductStatus.PRE_REGISTRATION,
            1L
        )
        given(productCommandService.create(request.toInput())).willReturn(product);
        mockMvc.perform(
            post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(handler().methodName("createProduct"))
            .andExpect(header().string("Location", "http://api.test.com:8080/v1/products/" + product.id))
            .andDo(
                document(
                    "product/create",
                    preprocessRequest(Preprocessors.prettyPrint()),
                    preprocessResponse(Preprocessors.prettyPrint()),
                    requestFields(
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량")
                    )
                )
            )
    }
}

