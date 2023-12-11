package com.travel.travelapp.domain.product.service

import com.travel.travelapp.domain.product.persistent.ProductDetails


data class CreateProductParam(
    val title: String,
    val price: Int,
    val imageUrl: String,
    val details: List<ProductDetails>,
    )