package com.travel.travelapp.product.api

import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.product.api.dto.CreateProductBody
import com.travel.travelapp.product.service.CreateProductParam
import com.travel.travelapp.product.service.ProductService
import com.travel.travelapp.security.ProduerAuthorize
import com.travel.travelapp.security.UserAuthorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Product Controller")
@ProduerAuthorize
@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {
    @Operation(summary = "상품 생성")
    @PostMapping
    fun create(@RequestBody body: CreateProductBody): ApiResponse<Unit> =
        ApiResponse.success(productService.create(
            with(body){
                CreateProductParam(
                    title = title,
                    price = price,
                    imageUrl = imageUrl,
                    details = details
                )
            }
        ))
}