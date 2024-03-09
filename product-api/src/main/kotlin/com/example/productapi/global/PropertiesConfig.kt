package com.example.productapi.global

import com.example.productapi.product.permission.ProductPermissionProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [ProductPermissionProperties::class])
class PropertiesConfig {
}
