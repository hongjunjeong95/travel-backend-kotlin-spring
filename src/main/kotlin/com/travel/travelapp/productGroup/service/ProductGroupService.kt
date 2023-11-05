package com.travel.travelapp.productGroup.service

import com.travel.travelapp.productGroup.persistent.ProductGroup
import com.travel.travelapp.productGroup.persistent.ProductGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductGroupService(
    private val productGroupRepository: ProductGroupRepository
) {
    @Transactional
    fun create(param: CreateProductGroupParam) {
        with(param){
            val product = ProductGroup(
                title = title,
                order = order
            )
            productGroupRepository.save(product)
        }
    }
}