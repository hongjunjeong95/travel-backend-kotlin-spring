package com.travel.travelapp.domain.productGroup.api.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AddProductToGroupBody(
    @Schema(description = "product id", example = "1")
    val productId: Long,
)

