package com.travel.travelapp.productGroupList.persistent

import com.fasterxml.jackson.annotation.JsonIgnore
import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.productGroup.persistent.ProductGroup
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "product_group_list",
    uniqueConstraints = [UniqueConstraint(name = "product_product_group_uk", columnNames = ["product_id", "product_group_id"])]
)
class ProductGroupList(
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product:Product,

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_group_id")
    val productGroup:ProductGroup
):BaseEntity()
