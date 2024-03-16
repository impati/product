package com.example.productdomain.product.domain

import com.example.productdomain.common.CreatedAudit
import jakarta.persistence.*

@Entity
@Table(name = "product_histories")
class ProductHistory(

    @Embedded
    val createdAudit: CreatedAudit,

    val name: String,

    val price: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    val status: ProductStatus,

    @Column(name = "product_id")
    val productId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_history_id")
    val id: Long? = null
) {

    companion object {
        fun from(product: Product): ProductHistory {
            return ProductHistory(
                CreatedAudit(product.updatedAudit.updatedAt, product.updatedAudit.updatedBy),
                product.name.value,
                product.price.value,
                product.status,
                product.id!!
            )
        }
    }
}
