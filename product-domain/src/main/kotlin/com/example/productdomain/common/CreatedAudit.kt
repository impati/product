package com.example.productdomain.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class CreatedAudit(

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "created_by")
    val createdBy: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreatedAudit

        if (createdAt != other.createdAt) return false
        if (createdBy != other.createdBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = createdAt.hashCode()
        result = 31 * result + createdBy.hashCode()
        return result
    }
}
