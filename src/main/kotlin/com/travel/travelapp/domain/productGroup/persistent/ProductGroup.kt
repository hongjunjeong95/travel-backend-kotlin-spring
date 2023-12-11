package com.travel.travelapp.domain.productGroup.persistent

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonIgnore
import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.domain.product.persistent.Product
import com.travel.travelapp.domain.productGroupList.persistent.ProductGroupList
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
): BaseEntity(){
    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ProductGroup::id)
        private val toStringProperties = arrayOf(
            ProductGroup::id,
        )
    }
}

object ProductGroupStatus {
    const val invisible = "invisible"
    const val visible = "visible"
}