package com.example.productdomain.product.domain


class Product(
    val name: String,
    val price: Int,
    val quantity: Int,
    val status: ProductStatus = ProductStatus.PRE_REGISTRATION,
) {

    init {
        ProductName(name)
        ProductPrice(price)
        ProductQuantity(quantity)
    }
}
