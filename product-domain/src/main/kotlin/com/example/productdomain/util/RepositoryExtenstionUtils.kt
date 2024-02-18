package com.example.productdomain.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: throw IllegalArgumentException("찾고자하는 엔티티를 찾지 못했습니다.")
}
