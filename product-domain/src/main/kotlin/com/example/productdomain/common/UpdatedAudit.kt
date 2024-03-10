package com.example.productdomain.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class UpdatedAudit(

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime,

    @Column(name = "updated_by")
    val updatedBy: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdatedAudit

        if (updatedAt != other.updatedAt) return false
        if (updatedBy != other.updatedBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = updatedAt.hashCode()
        result = 31 * result + updatedBy.hashCode()
        return result
    }
}
