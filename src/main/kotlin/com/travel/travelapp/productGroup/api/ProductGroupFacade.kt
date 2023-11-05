package com.travel.travelapp.productGroup.api

import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.productGroup.service.CreateProductGroupParam
import com.travel.travelapp.productGroup.service.ProductGroupService
import org.springframework.stereotype.Service

@Service
class ProductGroupFacade(
    private val productGroupService: ProductGroupService
) {
    fun create(body: CreateProductGroupBody) =
        productGroupService.create(
            with(body){
                CreateProductGroupParam(
                    title = title,
                    order = order,
                )
            }
        )
}