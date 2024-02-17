package com.example.productdomain.product.domain

enum class ProductStatus {

    PRE_REGISTRATION, // 상품 등록 후 판매되지는 않는 상태
    SELLING, // 판매 중인 상태
    SOLD_OUT, // 판매 중에서 재고가 없는 상태
    STOP, // 더 이상 상품을 판매하지 않는 상태
    DELETED // 삭제된 상태
}
