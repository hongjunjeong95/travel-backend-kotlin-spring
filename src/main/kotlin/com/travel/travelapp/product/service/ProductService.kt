package com.travel.travelapp.product.service

import com.travel.travelapp.common.service.CommonService
import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.product.persistent.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
):CommonService<Product>(productRepository) {
    fun create(param: Product) =
        productRepository.save(param)

    fun findByIdIn(ids: List<Long>) = productRepository.findByIdIn(ids)
}