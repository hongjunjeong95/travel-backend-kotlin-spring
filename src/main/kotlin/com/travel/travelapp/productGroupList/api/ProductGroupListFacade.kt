package com.travel.travelapp.productGroupList.api

import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.productGroup.service.CreateProductGroupParam
import com.travel.travelapp.productGroup.service.ProductGroupService
import com.travel.travelapp.productGroupList.service.ProductGroupListService
import org.springframework.stereotype.Service

@Service
class ProductGroupListFacade(
    private val productGroupListService: ProductGroupListService,
    private val productGroupService: ProductGroupService,
) {
    fun add() {
    }
}