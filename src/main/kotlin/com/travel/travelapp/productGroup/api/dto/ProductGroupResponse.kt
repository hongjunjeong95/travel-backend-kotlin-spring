package com.travel.travelapp.productGroup.api.dto

import com.travel.travelapp.domain.product.persistent.Product
import com.travel.travelapp.domain.product.persistent.ProductDetails
import com.travel.travelapp.productGroup.persistent.ProductGroup
import com.travel.travelapp.productGroup.persistent.ProductGroupStatus
import com.travel.travelapp.productGroupList.persistent.ProductGroupList

data class ProductGroupResponse(
    val id: Long,
    val title: String,
    val order: Int,
    val status: String,
    var product:  List<ProductModel>,
){
    companion object {
        fun of(productGroup: ProductGroup): ProductGroupResponse {
            checkNotNull(productGroup.id)
            return ProductGroupResponse(
                id = productGroup.id,
                title = productGroup.title,
                order = productGroup.order,
                status = productGroup.status,
                product = productGroup.productGroupLists.map { with(it.product){
                    ProductModel(
                        id = id,
                        title = title,
                        price = price,
                        imageUrl = imageUrl
                    )
                } }
            )
        }
    }
}

data class ProductModel(
    val id: Long,
    val title: String,
    val price: Int,
    val imageUrl: String,
){
    companion object {
        fun of(product: Product): ProductModel {
            checkNotNull(product.id)
            return ProductModel(
                id = product.id,
                title = product.title,
                price = product.price,
                imageUrl = product.imageUrl,
            )
        }
    }
}