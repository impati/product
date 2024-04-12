package com.example.productapi.product.application

import com.example.productapi.product.api.request.ProductCreateRequest
import com.example.productapi.product.api.request.ProductEditRequest
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.permission.ProductPermissionProperties
import com.example.productclient.permission.PermissionAdaptor
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductCommandService
import org.springframework.stereotype.Service

@Service
class ProductFacade(
    val productCommandService: ProductCommandService,
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
}
