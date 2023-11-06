package com.travel.travelapp.productGroup.api.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.travel.travelapp.productGroup.persistent.ProductGroup

data class ProductGroupsResponse(
    val items: List<ProductGroupResponse>
) {
    val size: Int
        @JsonIgnore get() = items.size
    fun get(index: Int): ProductGroupResponse = items[index]
    companion object {
        fun of(productGroups: List<ProductGroup>): ProductGroupsResponse{
            return ProductGroupsResponse(productGroups.map(ProductGroupResponse::of))
        }
    }
}
