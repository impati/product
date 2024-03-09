package com.example.productapi.product.api

import com.example.productapi.global.Member
import com.example.productapi.product.api.request.ProductCreateRequest
import com.example.productapi.product.api.request.ProductEditRequest
import com.example.productapi.product.api.response.ProductResponse
import com.example.productapi.product.application.ProductApplication
import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.application.ProductQueryService
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime.now

@Slf4j
@RestController
class ProductController(
    val productApplication: ProductApplication,
    val productQueryService: ProductQueryService
) {

    @PostMapping("/v1/products")
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ResponseEntity<Unit> {
        val member = Member("0000")
        val productId = productApplication.createProduct(request, CreatedAudit(now(), member.number))

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
        val member = Member("0000")

        return ResponseEntity.ok(productApplication.editProduct(productId, request, UpdatedAudit(now(), member.number)))
    }

    @DeleteMapping("/v1/products/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Unit> {
        val member = Member("0000")

        productApplication.deleteProduct(productId, UpdatedAudit(now(), member.number))

        return ResponseEntity.noContent().build()
    }


    private fun getUri(createdResourceId: Long): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdResourceId)
            .toUri();
    }
}
