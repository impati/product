package com.example.productdomain.common

import jakarta.persistence.Embeddable

@Embeddable
class ImagePath(
    val value: String
) {

    init {
        require(value.contains("https://")) {
            throw IllegalArgumentException("이미지 경로 형식이 올바르지 않습니다.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImagePath

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
