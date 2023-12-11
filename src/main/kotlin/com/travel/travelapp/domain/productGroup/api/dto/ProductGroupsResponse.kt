package com.travel.travelapp.domain.productGroup.api.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.travel.travelapp.domain.productGroup.persistent.ProductGroup

data class ProductGroupsResponse(
    val items: List<ProductGroupResponse>
) {
    fun get(index: Int): ProductGroupResponse = items[index]
    companion object {
        fun of(productGroups: List<ProductGroup>): ProductGroupsResponse {
            return ProductGroupsResponse(productGroups.map(ProductGroupResponse.Companion::of))
        }
    }
}
