package com.example.productsearch.product

import lombok.Getter
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime


@Getter
data class UpdatedAuditDocument(

    @Field(type = FieldType.Date)
    val updatedAt: LocalDateTime,

    @Field(type = FieldType.Keyword)
    val updatedBy: String,
) {
}
