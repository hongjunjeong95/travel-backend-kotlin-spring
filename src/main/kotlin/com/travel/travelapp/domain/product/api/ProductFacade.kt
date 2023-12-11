package com.travel.travelapp.domain.product.api

import com.travel.travelapp.domain.product.api.dto.CreateProductBody
import com.travel.travelapp.domain.product.persistent.Product
import com.travel.travelapp.domain.product.service.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductFacade(
    private val productService: ProductService,
) {
    @Transactional
    fun create(body: CreateProductBody) {
        productService.save(
            with(body){
                Product(
                    title = title,
                    price = price,
                    imageUrl = imageUrl,
                    details = details
                )
            }
        )
    }
}