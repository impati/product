package com.example.productsearch.domain.product.document

import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductStatus
import com.example.productsearch.domain.common.document.CreatedAuditDocument
import com.example.productsearch.domain.common.document.UpdatedAuditDocument
import lombok.Getter
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Getter
@Document(indexName = "products", createIndex = false)
data class ProductDocument(

    @Id
    @Field(type = FieldType.Long)
    val productId: Long,

    @Field(type = FieldType.Text)
    val name: String,

    @Field(type = FieldType.Object)
    val createdAudit: CreatedAuditDocument,

    @Field(type = FieldType.Object)
    val updatedAudit: UpdatedAuditDocument,

    @Field(type = FieldType.Integer)
    val price: Int,

    @Field(type = FieldType.Integer)
    val quantity: Int,

    @Field(type = FieldType.Keyword)
    val status: ProductStatus
) {

    companion object {

        fun from(product: Product): ProductDocument {
            return ProductDocument(
                product.id!!,
                product.name.value,
                CreatedAuditDocument.from(product.createdAudit),
                UpdatedAuditDocument.from(product.updatedAudit),
                product.price.value,
                product.quantity.value,
                product.status
            )
        }
    }
}
