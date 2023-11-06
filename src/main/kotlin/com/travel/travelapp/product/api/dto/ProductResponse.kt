package com.travel.travelapp.product.api.dto

import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.product.persistent.ProductDetails

data class ProductResponse(
        val id: Long,
        val title: String,
        val price: Int,
        val imageUrl: String,
        var details: MutableList<ProductDetails>
    ){
    companion object {
        fun of(product: Product): ProductResponse {
            checkNotNull(product.id)
            return ProductResponse(
                id = product.id,
                title = product.title,
                price = product.price,
                imageUrl = product.imageUrl,
                details = product.details
            )
        }
    }
}