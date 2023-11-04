package com.travel.travelapp.product.service

import com.travel.travelapp.product.persistent.ProductDetails


data class CreateProductParam(
    val title: String,
    val price: Int,
    val imageUrl: String,
    val details: List<ProductDetails>,
    )