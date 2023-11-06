package com.travel.travelapp.product.api.dto

import com.travel.travelapp.product.persistent.ProductDetails
import io.swagger.v3.oas.annotations.media.Schema


data class CreateProductBody(
    @Schema(description = "제목", example = "유럽 여행")
    val title: String,

    @Schema(description = "가격", example = "64000", type = "int")
    val price: Int,

    @Schema(description = "이미지 주소", example = "http://image.address")
    val imageUrl: String,

    val details: MutableList<ProductDetails>,
)