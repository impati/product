package com.example.productapi.product.api

import com.example.productapi.product.api.controller.ProductController
import com.example.productapi.product.api.request.ProductCreateRequest
import com.example.productapi.product.api.request.ProductEditRequest
import com.example.productapi.product.api.request.ProductRequest
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.application.ProductApplication
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductQueryService
import com.example.productdomain.product.domain.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
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
import java.time.LocalDateTime

@ExtendWith(value = [RestDocumentationExtension::class])
@AutoConfigureRestDocs(uriHost = "api.test.com", uriPort = 8080)
@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest @Autowired constructor(

    @Autowired
    val mockMvc: MockMvc,

    @MockkBean
    val productApplication: ProductApplication,

    @MockkBean
    val productQueryService: ProductQueryService,

    @Autowired
    val objectMapper: ObjectMapper
) {

    @Test
    @DisplayName("[POST] [/v1/products] 상품 생성 API")
    fun createProduct() {
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val request = ProductCreateRequest("test", 1000, 10, "0000")
        val product = Product(
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(10),
            ProductStatus.PRE_REGISTRATION,
            1L
        )

        every { productApplication.createProduct(request, any(CreatedAudit::class)) } returns product.id!!

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
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("memberNumber").description("멤버 번호")
                    )
                )
            )
    }

    @Test
    @DisplayName("[GET] [/v1/products/{productId}] 상품 조회 API")
    fun getProduct() {
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val productId = 1L
        val product = Product(
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(10),
            ProductStatus.PRE_REGISTRATION,
            1L
        )
        every { (productQueryService.getProduct(productId)) } returns (product);

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
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val request = ProductEditRequest("test", 1000, 10, ProductStatus.STOP, "0000")
        val product = Product(
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ProductStatus.PRE_REGISTRATION,
            productId
        )
        every {
            productApplication.editProduct(productId, request, any(UpdatedAudit::class))
        } returns (ProductResponse.from(product))

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
                        fieldWithPath("status").description("상품 상태"),
                        fieldWithPath("memberNumber").description("멤버 번호")
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

    @Test
    fun deleteProduct() {
        val productId = 1L
        val request = ProductRequest("0000")

        every {
            productApplication.deleteProduct(productId, any(UpdatedAudit::class))
        } just Runs

        mockMvc.perform(
            delete("/v1/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
            .andExpect(handler().methodName("deleteProduct"))
            .andDo(
                document(
                    "product/delete",
                    preprocessRequest(Preprocessors.prettyPrint()),
                    preprocessResponse(Preprocessors.prettyPrint()),
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    requestFields(
                        fieldWithPath("memberNumber").description("멤버 번호")
                    ),
                )
            )
    }
}

