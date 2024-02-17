package com.example.productdomain.product.domain

import jakarta.persistence.*


@Entity
@Table(name = "products")
class Product(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "name"))
    val name: ProductName,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "price"))
    val price: ProductPrice,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "quantity"))
    val quantity: ProductQuantity,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    val status: ProductStatus = ProductStatus.PRE_REGISTRATION,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null,
) {
}
