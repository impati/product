package com.example.productclient.permission

import com.example.productclient.feign.BearerAuthRequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class BearerAuthorizationConfiguration(
    @Value("\${external.permission.api-key}")
    val apiKey: String
) {

    @Bean
    fun bearerAuthorizationConfiguration(): BearerAuthRequestInterceptor {
        return BearerAuthRequestInterceptor(apiKey)
    }
}
