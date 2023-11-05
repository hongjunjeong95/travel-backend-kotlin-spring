package com.travel.travelapp.productGroup.api

import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.security.ProduerAuthorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Product Group Controller")
@ProduerAuthorize
@RestController
@RequestMapping("/api/v1/product-groups")
class ProductGroupController(
    private val productGroupFacade: ProductGroupFacade
) {
    @Operation(summary = "상품 그룹 생성")
    @PostMapping
    fun create(@RequestBody body: CreateProductGroupBody): ApiResponse<Unit> =
        ApiResponse.success(productGroupFacade.create(body))
}