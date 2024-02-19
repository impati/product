package com.example.productapi.product.controller

import com.example.productapi.product.controller.request.ProductCreateRequest
import com.example.productapi.product.controller.request.ProductEditRequest
import com.example.productapi.product.controller.response.ProductResponse
import com.example.productdomain.product.application.ProductCommandService
import com.example.productdomain.product.application.ProductQueryService
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
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
    @MockBean
    val productQueryService: ProductQueryService,
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

    @Test
    @DisplayName("[GET] [/v1/products/{productId}] 상품 조회 API")
    fun getProduct() {
        val productId = 1L
        val product = Product(
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ProductStatus.PRE_REGISTRATION,
            productId
        )
        given(productQueryService.getProduct(productId)).willReturn(product);

        mockMvc.perform(get("/v1/products/{productId}", productId))
            .andExpect(status().isOk)
            .andExpect(handler().methodName("getProduct"))
            .andExpect(content().string(objectMapper.writeValueAsString(ProductResponse.from(product))))
            .andDo(
                document(
                    "product/get",
                    preprocessRequest(Preprocessors.prettyPrint()),
                    preprocessResponse(Preprocessors.prettyPrint()),
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    responseFields(
                        fieldWithPath("productId").description("상품 ID"),
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("status").description("상품 상태")
                    )
                )
            )
    }

    @Test
    @DisplayName("[PUT] [/v1/products/{productId}] 상품 수정 API")
    fun editProduct() {
        val productId = 1L
        val request = ProductEditRequest("test", 1000, 10, ProductStatus.STOP)
        val product = Product(
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ProductStatus.PRE_REGISTRATION,
            productId
        )
        given(productCommandService.edit(productId, request.toInput())).willReturn(product);

        mockMvc.perform(
            put("/v1/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(handler().methodName("editProduct"))
            .andExpect(content().string(objectMapper.writeValueAsString(ProductResponse.from(product))))
            .andDo(
                document(
                    "product/edit",
                    preprocessRequest(Preprocessors.prettyPrint()),
                    preprocessResponse(Preprocessors.prettyPrint()),
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    requestFields(
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("status").description("상품 상태")
                    ),
                    responseFields(
                        fieldWithPath("productId").description("상품 ID"),
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("status").description("상품 상태")
                    )
                )
            )
    }
}

