package com.travel.travelapp.domain.productGroup.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.domain.productGroup.persistent.ProductGroup
import com.travel.travelapp.domain.productGroup.persistent.ProductGroupRepository
import org.springframework.stereotype.Service

@Service
class ProductGroupService(
    private val productGroupRepository: ProductGroupRepository
):CommonService<ProductGroup>(productGroupRepository) {
    fun findByStatusOrderByOrderDesc(status:String) = productGroupRepository.findByStatusOrderByOrderDesc(status) ?: mutableListOf()
}