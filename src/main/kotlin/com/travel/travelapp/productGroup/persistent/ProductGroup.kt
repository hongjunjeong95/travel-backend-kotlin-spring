package com.travel.travelapp.productGroup.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "product_group")
data class ProductGroup(
    @Column(name = "title")
    var title: String,

    @Column(name = "\"order\"")
    var order: Int,

    @Column(name = "status")
    @ColumnDefault(ProductGroupStatus.invisible)
    var status: String = ProductGroupStatus.invisible,

    @OneToMany
    @JoinColumn(name = "product_group_id")
    val productGroupList:  List<ProductGroupList> = emptyList()
): BaseEntity()

object ProductGroupStatus {
    const val invisible = "invisible"
    const val visible = "visible"
}