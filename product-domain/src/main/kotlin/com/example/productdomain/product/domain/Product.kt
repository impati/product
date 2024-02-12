package com.example.productdomain.product.domain

import jakarta.persistence.*


@Entity
@Table(name = "products")
class Product(

    @Column(name = "name")
    val name: String,

    @Column(name = "price")
    val price: Int,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "status")
    val status: ProductStatus = ProductStatus.PRE_REGISTRATION,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    init {
        ProductName(name)
        ProductPrice(price)
        ProductQuantity(quantity)
    }
}
