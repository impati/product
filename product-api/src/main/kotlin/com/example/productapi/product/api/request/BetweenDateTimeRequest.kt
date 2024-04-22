package com.example.productapi.product.api.request

import java.time.LocalDateTime

data class BetweenDateTimeRequest(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
) {
}
