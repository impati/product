package com.example.productdomain.product.domain

import jakarta.persistence.Embeddable


@Embeddable
class ProductName(
    val value: String
) {

    init {
        require(value.length in MIN_LENGTH..MAX_LENGTH) {
            throw IllegalArgumentException("상품 이름은 ${MIN_LENGTH}글자 이상 ${MAX_LENGTH}글자 이하여야합니다.")
        }
    }

    companion object {
        private const val MAX_LENGTH = 50
        private const val MIN_LENGTH = 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductName

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
