package com.example.productsearch.domain.common.document

import com.example.productdomain.common.CreatedAudit
import lombok.Getter
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Getter
data class CreatedAuditDocument(

    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis])
    val createdAt: LocalDateTime,

    @Field(type = FieldType.Keyword)
    val createdBy: String,
) {

    companion object {

        fun from(createdAudit: CreatedAudit): CreatedAuditDocument {

            return CreatedAuditDocument(
                createdAudit.createdAt,
                createdAudit.createdBy
            )
        }
    }
}
