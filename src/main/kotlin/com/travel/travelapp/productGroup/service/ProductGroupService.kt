package com.travel.travelapp.productGroup.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.productGroup.persistent.ProductGroup
import com.travel.travelapp.productGroup.persistent.ProductGroupRepository
import org.springframework.stereotype.Service

@Service
class ProductGroupService(
    private val productGroupRepository: ProductGroupRepository
):CommonService<ProductGroup>(productGroupRepository) {
    fun create(param: ProductGroup) =
        productGroupRepository.save(param)

    fun findByStatusOrderByOrderDesc(status:String) = productGroupRepository.findByStatusOrderByOrderDesc(status) ?: mutableListOf()
}