package com.travel.travelapp.domain.product.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.domain.product.persistent.Product
import com.travel.travelapp.domain.product.persistent.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
):CommonService<Product>(productRepository) {
    fun findByIdIn(ids: List<Long>) = productRepository.findByIdIn(ids)
}