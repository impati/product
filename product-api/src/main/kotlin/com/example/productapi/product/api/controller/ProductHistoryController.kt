package com.example.productapi.product.api.controller

import com.example.productapi.product.api.response.ProductHistoryResponse
import com.example.productdomain.product.application.ProductHistoryQueryService
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class ProductHistoryController(
    val productHistoryQueryService: ProductHistoryQueryService
) {

    @GetMapping("/v1/products/{productId}/histories")
    fun getProductHistories(@PathVariable productId: Long): List<ProductHistoryResponse> {

        return productHistoryQueryService.getProductHistories(productId)
            .map { ProductHistoryResponse.from(it) }
            .toList()
    }
}
