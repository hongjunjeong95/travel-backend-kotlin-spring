package com.travel.travelapp.domain.product.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface ProductRepository: BaseRepository<Product, Long>{
    fun findByIdIn(ids : List<Long>) : List<Product>
}