package com.example.productdomain.product

import com.example.productdomain.product.domain.Product
import com.example.productdomain.product.domain.ProductName
import com.example.productdomain.product.domain.ProductPrice
import com.example.productdomain.product.domain.ProductQuantity

fun createDefaultProduct(): Product {
    return Product(ProductName("test"), ProductPrice(1000), ProductQuantity(100))
}
