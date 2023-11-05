package com.travel.travelapp.product.api

import com.travel.travelapp.product.api.dto.CreateProductBody
import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.product.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductFacade(
    private val productService: ProductService,
) {
    fun create(body: CreateProductBody) {
        productService.create(
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