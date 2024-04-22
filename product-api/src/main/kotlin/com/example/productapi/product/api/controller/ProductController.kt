package com.example.productapi.product.api.controller

import com.example.productapi.product.api.request.ProductCreateRequest
import com.example.productapi.product.api.request.ProductEditRequest
import com.example.productapi.product.api.request.ProductRequest
import com.example.productapi.product.api.request.ProductSearchRequest
import com.example.productapi.product.api.response.ProductDetailResponses
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.application.ProductFacade
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductQueryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime.now

@RestController
class ProductController(
    val productFacade: ProductFacade,
    val productQueryService: ProductQueryService
) {

    @PostMapping("/v1/products")
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ResponseEntity<Unit> {
        val productId = productFacade.createProduct(request, CreatedAudit(now(), request.memberNumber))

        return ResponseEntity.created(getUri(productId)).build()
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
        val product = productFacade.editProduct(productId, request, UpdatedAudit(now(), request.memberNumber))

        return ResponseEntity.ok(product)
    }

    @DeleteMapping("/v1/products/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody request: ProductRequest
    ): ResponseEntity<Unit> {
        productFacade.deleteProduct(productId, UpdatedAudit(now(), request.memberNumber))

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/v1/products")
    fun searchProduct(@ModelAttribute productSearchRequest: ProductSearchRequest): ResponseEntity<ProductDetailResponses> {
        val searchProduct = productFacade.searchProduct(productSearchRequest)

        return ResponseEntity.ok(searchProduct)
    }


    private fun getUri(createdResourceId: Long): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdResourceId)
            .toUri();
    }
}
