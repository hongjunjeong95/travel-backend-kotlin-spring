package com.travel.travelapp.product.service

import com.travel.travelapp.product.persistent.Product
import com.travel.travelapp.product.persistent.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun create(param: CreateProductParam) {
        with(param){
            val product = Product(
                title = title,
                price = price,
                imageUrl = imageUrl,
                details = details
            )

            productRepository.save(product)
        }
    }
}