package com.example.productclient

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [FeignConfig::class])
class FeignConfig {
}
