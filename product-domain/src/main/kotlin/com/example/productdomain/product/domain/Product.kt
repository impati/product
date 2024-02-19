package com.example.productdomain.product.domain

import jakarta.persistence.*


@Entity
@Table(name = "products")
class Product(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "name"))
    var name: ProductName,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "price"))
    var price: ProductPrice,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "quantity"))
    var quantity: ProductQuantity,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    var status: ProductStatus = ProductStatus.PRE_REGISTRATION,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null,
) {

    fun update(name: String, price: Int, quantity: Int, status: ProductStatus) {
        this.name = ProductName(name);
        this.price = ProductPrice(price);
        this.quantity = ProductQuantity(quantity);
        this.status = status;
    }
}
