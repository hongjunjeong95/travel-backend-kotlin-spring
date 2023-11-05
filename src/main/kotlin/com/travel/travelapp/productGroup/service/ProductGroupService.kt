package com.travel.travelapp.productGroup.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.productGroup.persistent.ProductGroup
import com.travel.travelapp.productGroup.persistent.ProductGroupRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ProductGroupService(
    private val productGroupRepository: ProductGroupRepository
):CommonService<ProductGroup>(productGroupRepository) {
    fun create(param: ProductGroup) =
        productGroupRepository.save(param)

    fun update(param: CreateProductGroupParam) {
        with(param){
            val product = ProductGroup(
                title = title,
                order = order
            )
            productGroupRepository.save(product)
        }
    }
}