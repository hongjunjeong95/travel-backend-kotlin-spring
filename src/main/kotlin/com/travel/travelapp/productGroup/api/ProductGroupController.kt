package com.travel.travelapp.productGroup.api

import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.productGroup.api.dto.AddProductToGroupBody
import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.security.ProduerAuthorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Product Group")
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

    @Operation(summary = "상품을 그룹에 추가")
    @PutMapping("/{id}/add")
    fun addProductToGroup(
        @PathVariable id: Long,
        @RequestBody body: AddProductToGroupBody
    ): ApiResponse<Unit> =
        ApiResponse.success(productGroupFacade.addProductToGroup(id, body))

//    @Operation(summary = "상품 그룹 리스트 조회")
//    @GetMapping
//    fun findAll(): ApiResponse<Unit> =
//        ApiResponse.success(productGroupFacade.findAll())
}