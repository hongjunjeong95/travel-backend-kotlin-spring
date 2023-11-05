package com.travel.travelapp.productGroupList.api

import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.productGroup.api.ProductGroupFacade
import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.security.ProduerAuthorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Product Group List")
@ProduerAuthorize
@RestController
@RequestMapping("/api/v1/product-group-lists")
class ProductGroupListController (
    private val productGroupListFacade: ProductGroupListFacade
    ) {
//        @Operation(summary = "상품 그룹 생성")
//        @GetMapping
//        fun findAll(): ApiResponse<Unit> =
//            ApiResponse.success(productGroupListFacade.findAll())
    }