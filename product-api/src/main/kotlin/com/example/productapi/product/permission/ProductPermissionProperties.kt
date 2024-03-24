package com.example.productapi.product.permission

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "permission.product")
class ProductPermissionProperties(
    val createPermissionId: Long,
    val editPermissionId: Long,
    val deletePermissionId: Long
) {
}
