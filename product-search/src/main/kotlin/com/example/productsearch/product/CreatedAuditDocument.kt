package com.example.productsearch.product

import lombok.Getter
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Getter
data class CreatedAuditDocument(

    @Field(type = FieldType.Date)
    val createdAt: LocalDateTime,

    @Field(type = FieldType.Keyword)
    val createdBy: String,
) {
}
