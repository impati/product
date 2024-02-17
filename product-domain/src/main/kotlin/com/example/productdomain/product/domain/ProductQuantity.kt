package com.example.productdomain.product.domain

import jakarta.persistence.Embeddable

@Embeddable
class ProductQuantity(
    val value: Int
) {

    init {
        require(value in MIN_PRICE..MAX_PRICE) {
            throw IllegalArgumentException("상품 수량은 ${MIN_PRICE}보다 크거나 같고 ${MAX_PRICE}보다 작아야합니다.")
        }
    }

    companion object {
        private const val MAX_PRICE = 1_000_000_000
        private const val MIN_PRICE = 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductQuantity

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }
}
