package com.example.productdomain.product.domain

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.UpdatedAudit
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction


@Entity
@Table(name = "products")
@SQLRestriction(value = "status <>  'DELETED'")
class Product(

    @Embedded
    val createdAudit: CreatedAudit,

    @Embedded
    var updatedAudit: UpdatedAudit,

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
    val id: Long? = null
) {

    fun update(
        updatedAudit: UpdatedAudit,
        name: String,
        price: Int,
        quantity: Int,
        status: ProductStatus
    ) {
        require(this.status != ProductStatus.DELETED) { "상품이 이미 삭제되어 변경할 수 없습니다." }
        this.name = ProductName(name)
        this.price = ProductPrice(price)
        this.quantity = ProductQuantity(quantity)
        this.status = status
        this.updatedAudit = updatedAudit
    }

    fun delete(updatedAudit: UpdatedAudit) {
        this.status = ProductStatus.DELETED
        this.updatedAudit = updatedAudit
    }
}
