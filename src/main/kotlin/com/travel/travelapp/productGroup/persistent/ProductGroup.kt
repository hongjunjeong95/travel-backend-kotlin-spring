package com.travel.travelapp.productGroup.persistent

import com.fasterxml.jackson.annotation.JsonIgnore
import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "product_group")
class ProductGroup(
    @Column(name = "title")
    var title: String,

    @Column(name = "\"order\"")
    var order: Int,

    @Column(name = "status")
    @ColumnDefault(ProductGroupStatus.invisible)
    var status: String = ProductGroupStatus.invisible,

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id")
    var productGroupLists:  MutableList<ProductGroupList> = mutableListOf()
): BaseEntity()

object ProductGroupStatus {
    const val invisible = "invisible"
    const val visible = "visible"
}