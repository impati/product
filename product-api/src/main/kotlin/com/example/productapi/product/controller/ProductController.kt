package com.example.productapi.product.controller

import com.example.productapi.product.controller.request.ProductCreateRequest
import com.example.productapi.product.controller.request.ProductEditRequest
import com.example.productapi.product.controller.response.ProductResponse
import com.example.productdomain.product.application.ProductCommandService
import com.example.productdomain.product.application.ProductQueryService
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Slf4j
@RestController
class ProductController(
    val productCommandService: ProductCommandService,
    val productQueryService: ProductQueryService
) {

    @PostMapping("/v1/products")
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ResponseEntity<Unit> {
        val productId = productCommandService.create(request.toInput()).id

        return ResponseEntity.created(getUri(productId!!)).build()
    }

    @GetMapping("/v1/products/{productId}")
    fun getProduct(@PathVariable productId: Long): ResponseEntity<ProductResponse> {
        val product = productQueryService.getProduct(productId)

        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @PutMapping("/v1/products/{productId}")
    fun editProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody request: ProductEditRequest
    ): ResponseEntity<ProductResponse> {
        val product = productCommandService.edit(productId, request.toInput())

        return ResponseEntity.ok(ProductResponse.from(product))
    }

    private fun getUri(createdResourceId: Long): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdResourceId)
            .toUri();
    }
}
