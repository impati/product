package com.example.productdomain.product.domain

import com.example.productdomain.common.CreatedAudit
import com.example.productdomain.common.ImagePath
import com.example.productdomain.common.UpdatedAudit
import com.example.productdomain.product.exception.ProductOptimisticException
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction


@Entity
@Table(
    name = "products",
    indexes = [
        Index(name = "idx__status", columnList = "status")
    ]
)
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

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "image"))
    var imagePath: ImagePath,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    var status: ProductStatus = ProductStatus.PRE_REGISTRATION,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null,

    @Version
    var version: Long = 0L,
) {

    fun update(
        updatedAudit: UpdatedAudit,
        name: String,
        price: Int,
        quantity: Int,
        imagePath: String,
        status: ProductStatus
    ) {
        require(this.status != ProductStatus.DELETED) { "상품이 이미 삭제되어 변경할 수 없습니다." }
        this.name = ProductName(name)
        this.price = ProductPrice(price)
        this.quantity = ProductQuantity(quantity)
        this.imagePath = ImagePath(imagePath)
        this.status = status
        this.updatedAudit = updatedAudit
    }

    fun delete(updatedAudit: UpdatedAudit) {
        this.status = ProductStatus.DELETED
        this.updatedAudit = updatedAudit
    }

    fun checkVersion(version: Long) {
        require(this.version == version) {
            throw ProductOptimisticException("수정에 실패했습니다. 다시 시도해주세요.")
        }
    }
}
