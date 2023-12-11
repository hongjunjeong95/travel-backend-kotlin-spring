package com.travel.travelapp.domain.productGroupList.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.domain.productGroupList.persistent.ProductGroupList
import com.travel.travelapp.domain.productGroupList.persistent.ProductGroupListRepository
import org.springframework.stereotype.Service

@Service
class ProductGroupListService(
    private val productGroupListRepository: ProductGroupListRepository
): CommonService<ProductGroupList>(productGroupListRepository)