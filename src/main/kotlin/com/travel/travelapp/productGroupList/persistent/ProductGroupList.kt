package com.travel.travelapp.productGroupList.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.productGroup.persistent.ProductGroup
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "product_group_list",
    uniqueConstraints = [UniqueConstraint(name = "product_product_group_uk", columnNames = ["product_id", "product_group_id"])]
)
data class ProductGroupList(
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product:Product,

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    val productGroup:ProductGroup
):BaseEntity()
