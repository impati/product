package com.example.productsearch.domain.common.document

import com.example.productdomain.common.UpdatedAudit
import lombok.Getter
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime


@Getter
data class UpdatedAuditDocument(

    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis])
    val updatedAt: LocalDateTime,

    @Field(type = FieldType.Keyword)
    val updatedBy: String,
) {

    companion object {

        fun from(updatedAudit: UpdatedAudit): UpdatedAuditDocument {

            return UpdatedAuditDocument(
                updatedAudit.updatedAt,
                updatedAudit.updatedBy
            )
        }
    }
}
