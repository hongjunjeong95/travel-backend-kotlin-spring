package com.travel.travelapp.productGroup.api.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateProductGroupBody(
    @Schema(description = "제목", example = "해외 인기 상품 최저가 보장 특가전")
    val title: String,

    @Schema(description = "메이 테마에서 순서", example = "1", type = "int")
    val order: Int,
)

data class AddProductToGroupBody(
    @Schema(description = "product id", example = "1")
    val productId: Long,
)
