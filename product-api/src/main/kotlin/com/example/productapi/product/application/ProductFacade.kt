package com.example.productapi.product.application

import com.example.productapi.product.api.request.ProductCreateRequest
import com.example.productapi.product.api.request.ProductEditRequest
import com.example.productapi.product.api.request.ProductSearchRequest
import com.example.productapi.product.api.response.ProductDetailResponse
import com.example.productapi.product.api.response.ProductDetailResponses
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.permission.ProductPermissionProperties
import com.example.productclient.permission.PermissionAdaptor
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductCommandService
import com.example.productsearch.condition.DateCondition
import com.example.productsearch.condition.KeywordCondition
import com.example.productsearch.condition.NumberCondition
import com.example.productsearch.condition.TextCondition
import com.example.productsearch.domain.common.condition.CreatedAuditCondition
import com.example.productsearch.domain.product.condition.ProductCondition
import com.example.productsearch.domain.product.service.ProductDocumentService
import org.springframework.stereotype.Service

@Service
class ProductFacade(
    val productCommandService: ProductCommandService,
    val productDocumentService: ProductDocumentService,
    val productPermissionProperties: ProductPermissionProperties,
    val productAdaptor: PermissionAdaptor
) {

    fun createProduct(request: ProductCreateRequest, createdAudit: CreatedAudit): Long {
        require(productAdaptor.hasPermission(productPermissionProperties.createPermissionId, createdAudit.createdBy))
        { "사용자가 상품 생성 권한을 가지고 있지 않습니다: 멤버번호 ${createdAudit.createdBy}" }

        return productCommandService.create(request.toInput(createdAudit)).id!!
    }

    fun editProduct(productId: Long, request: ProductEditRequest, updatedAudit: UpdatedAudit): ProductResponse {
        require(productAdaptor.hasPermission(productPermissionProperties.editPermissionId, updatedAudit.updatedBy))
        { "사용자가 상품 수정 권한을 가지고 있지 않습니다: 멤버번호 ${updatedAudit.updatedBy}" }

        val product = productCommandService.edit(productId, request.toInput(), updatedAudit)

        return ProductResponse.from(product)
    }

    fun deleteProduct(productId: Long, updatedAudit: UpdatedAudit) {
        require(productAdaptor.hasPermission(productPermissionProperties.deletePermissionId, updatedAudit.updatedBy))
        { "사용자가 상품 삭제 권한을 가지고 있지 않습니다: 멤버번호 ${updatedAudit.updatedBy}" }

        productCommandService.delete(productId, updatedAudit)
    }

    fun searchProduct(request: ProductSearchRequest): ProductDetailResponses {
        val productCondition = ProductCondition(
            productId = NumberCondition.eq(request.productId),
            name = TextCondition.contain(request.productName),
            createdAudit = buildProductCreatedTimeRequest(request),
            price = buildProductPriceCondition(request),
            status = KeywordCondition.eq(request.productStatus?.name)
        )

        val productDetailResponses = productDocumentService.search(productCondition)
            .map {
                ProductDetailResponse(
                    it.productId,
                    it.name,
                    it.price,
                    it.quantity,
                    it.status,
                    it.createdAudit.createdBy,
                    it.createdAudit.createdAt,
                    it.updatedAudit.updatedBy,
                    it.updatedAudit.updatedAt,
                )
            }
            .toList()

        return ProductDetailResponses(productDetailResponses)
    }


    private fun buildProductCreatedTimeRequest(request: ProductSearchRequest): CreatedAuditCondition? {
        return request.productCreatedTime?.let {
            CreatedAuditCondition(
                DateCondition.ofBetween(request.productCreatedTime.startAt, request.productCreatedTime.endAt),
                KeywordCondition.eq(request.createdBy)
            )
        }
    }

    private fun buildProductPriceCondition(request: ProductSearchRequest): NumberCondition? {

        return request.productPrice?.let {
            NumberCondition.operate(
                request.productPrice.price,
                request.productPrice.operator
            )
        }
    }
}
