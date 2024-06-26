package com.example.productapi.product.api.controller

import com.example.productapi.product.api.controller.RestDocsUtils.Companion.prettyDocument
import com.example.productapi.product.api.request.*
import com.example.productapi.product.api.response.ProductDetailResponse
import com.example.productapi.product.api.response.ProductDetailResponses
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.application.ProductFacade
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductQueryService
import com.example.productdomain.product.domain.*
import com.example.productsearch.condition.operator.NumberOperator
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
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
    val productFacade: ProductFacade,

    @MockkBean
    val productQueryService: ProductQueryService,

    @Autowired
    val objectMapper: ObjectMapper
) {

    @Test
    @DisplayName("[POST] [/v1/products] 상품 생성 API")
    fun createProduct() {
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val request = ProductCreateRequest("test", 1000, 10, "0000", "https://image")
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

        every { productFacade.createProduct(request, any(CreatedAudit::class)) } returns product.id!!

        mockMvc.perform(
            post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(handler().methodName("createProduct"))
            .andExpect(header().string("Location", "http://api.test.com:8080/v1/products/" + product.id))
            .andDo(
                prettyDocument(
                    "product/create",
                    requestFields(
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("memberNumber").description("멤버 번호"),
                        fieldWithPath("imagePath").description("상품 이미지 경로"),
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
            ImagePath("https://localhost"),
            ProductStatus.PRE_REGISTRATION,
            1L
        )
        every { (productQueryService.getProduct(productId)) } returns (product);

        mockMvc.perform(get("/v1/products/{productId}", productId))
            .andExpect(status().isOk)
            .andExpect(handler().methodName("getProduct"))
            .andExpect(content().string(objectMapper.writeValueAsString(ProductResponse.from(product))))
            .andDo(
                prettyDocument(
                    "product/get",
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    responseFields(
                        fieldWithPath("productId").description("상품 ID"),
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("status").description("상품 상태"),
                        fieldWithPath("imagePath").description("상품 이미지 경로"),
                        fieldWithPath("version").description("상품 수정 버전")
                    )
                )
            )
    }

    @Test
    @DisplayName("[PUT] [/v1/products/{productId}] 상품 수정 API")
    fun editProduct() {
        val productId = 1L
        val now = LocalDateTime.of(2024, 3, 9, 0, 0)
        val request = ProductEditRequest(
            "test",
            1000,
            10,
            ProductStatus.STOP,
            "0000",
            "https://localhost",
            0
        )
        val product = Product(
            CreatedAudit(now, "0000"),
            UpdatedAudit(now, "0000"),
            ProductName("test"),
            ProductPrice(1000),
            ProductQuantity(100),
            ImagePath("https://localhost"),
            ProductStatus.PRE_REGISTRATION,
            productId
        )
        every {
            productFacade.editProduct(productId, request, any(UpdatedAudit::class))
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
                prettyDocument(
                    "product/edit",
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    requestFields(
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("status").description("상품 상태"),
                        fieldWithPath("memberNumber").description("멤버 번호"),
                        fieldWithPath("imagePath").description("상품 이미지 경로"),
                        fieldWithPath("version").description("상품 수정 버전"),
                    ),
                    responseFields(
                        fieldWithPath("productId").description("상품 ID"),
                        fieldWithPath("name").description("상품 이름"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("quantity").description("상품 수량"),
                        fieldWithPath("imagePath").description("상품 이미지 경로"),
                        fieldWithPath("status").description("상품 상태"),
                        fieldWithPath("version").description("상품 수정 버전")
                    )
                )
            )
    }

    @Test
    @DisplayName("[DELETE] [/v1/products/{productId}] 상품 삭제 API")
    fun deleteProduct() {
        val productId = 1L
        val request = ProductRequest("0000")

        every {
            productFacade.deleteProduct(productId, any(UpdatedAudit::class))
        } just Runs

        mockMvc.perform(
            delete("/v1/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
            .andExpect(handler().methodName("deleteProduct"))
            .andDo(
                prettyDocument(
                    "product/delete",
                    pathParameters(parameterWithName("productId").description("상품 ID")),
                    requestFields(
                        fieldWithPath("memberNumber").description("멤버 번호")
                    ),
                )
            )
    }

    @Test
    @DisplayName("[GET] [/v1/products] 상품 검색 API")
    fun searchProduct() {
        val request = ProductSearchRequest(
            createdBy = "root",
            productName = "hello world",
            productPrice = NumberOperateRequest(100, NumberOperator.EQUAL),
            productStatus = ProductStatus.PRE_REGISTRATION,
            productId = 1L,
            productCreatedTime = BetweenDateTimeRequest(
                LocalDateTime.of(2024, 4, 1, 0, 0),
                LocalDateTime.of(2024, 4, 14, 0, 0)
            )
        )
        val response = ProductDetailResponses(
            listOf(
                ProductDetailResponse(
                    1L,
                    "hello world",
                    100,
                    1000,
                    ProductStatus.PRE_REGISTRATION,
                    "root",
                    LocalDateTime.of(2024, 4, 14, 0, 0),
                    "root",
                    LocalDateTime.of(2024, 4, 14, 0, 0)
                )
            )
        )
        every { productFacade.searchProduct(request) } returns (response)

        mockMvc.perform(
            get("/v1/products")
                .param("createdBy", request.createdBy)
                .param("productName", request.productName)
                .param("productPrice.price", request.productPrice?.price.toString())
                .param("productPrice.operator", request.productPrice?.operator?.name)
                .param("productStatus", request.productStatus?.name)
                .param("productId", request.productId.toString())
                .param("productCreatedTime.startAt", request.productCreatedTime?.startAt.toString())
                .param("productCreatedTime.endAt", request.productCreatedTime?.endAt.toString())

        )
            .andExpect(status().isOk)
            .andExpect(handler().methodName("searchProduct"))
            .andDo(
                prettyDocument(
                    "product/search",
                    relaxedQueryParameters(
                        parameterWithName("createdBy").description("상품을 생성한 멤버"),
                        parameterWithName("productName").description("상품 이름"),
                        parameterWithName("productPrice.price").description("상품 가격"),
                        parameterWithName("productPrice.operator").description("상품 가격 검색 오퍼레이터"),
                        parameterWithName("productStatus").description("상품 선택"),
                        parameterWithName("productId").description("상품 번호"),
                        parameterWithName("productCreatedTime.startAt").description("상품 생성 검색 시작 날짜"),
                        parameterWithName("productCreatedTime.endAt").description("상품 생성 검색 끝 날짜")
                    ),
                    responseFields(
                        fieldWithPath("products[].productId").type(JsonFieldType.NUMBER).description("상품 번호"),
                        fieldWithPath("products[].name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("products[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                        fieldWithPath("products[].quantity").type(JsonFieldType.NUMBER).description("상품 수량"),
                        fieldWithPath("products[].status").type(JsonFieldType.STRING).description("상품 상태"),
                        fieldWithPath("products[].createdBy").type(JsonFieldType.STRING).description("상품을 생성한 멤버"),
                        fieldWithPath("products[].createdAt").type(JsonFieldType.STRING).description("상품이 생성된 날짜"),
                        fieldWithPath("products[].updatedBy").type(JsonFieldType.STRING).description("상품을 수정한 멤버"),
                        fieldWithPath("products[].updatedAt").type(JsonFieldType.STRING).description("상품을 수정한 날짜")
                    )
                )
            )
    }
}

