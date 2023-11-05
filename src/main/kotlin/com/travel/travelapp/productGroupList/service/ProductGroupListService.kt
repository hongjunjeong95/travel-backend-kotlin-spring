package com.travel.travelapp.productGroupList.service

import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import com.travel.travelapp.productGroupList.persistent.ProductGroupListRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductGroupListService(
    private val productGroupListRepository: ProductGroupListRepository
) {
    @Transactional
    fun create(param: ProductGroupList) =
        productGroupListRepository.save(param)
}