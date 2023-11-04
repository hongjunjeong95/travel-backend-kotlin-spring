package com.travel.travelapp.productGroupList.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.productGroup.persistent.ProductGroup
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "product_group_list")
data class ProductGroupList(
    @ManyToOne
//    @JoinColumn(name = "product_id")
    val product:Product,

    @ManyToOne
//    @JoinColumn(name = "product_group_id")
    val productGroup:ProductGroup
):BaseEntity()
