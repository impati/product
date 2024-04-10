package com.example.productsearch.product

import com.example.productdomain.product.domain.ProductStatus
import lombok.Getter
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Getter
@Document(indexName = "products", createIndex = false)
data class ProductDocument(

    @Id
    val productId: Long,

    @Field(type = FieldType.Text)
    val productName: String,

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
}
