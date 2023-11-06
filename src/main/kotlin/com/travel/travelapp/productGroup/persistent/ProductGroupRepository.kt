package com.travel.travelapp.productGroup.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface ProductGroupRepository: BaseRepository<ProductGroup, Long>{
    fun findByStatusOrderByOrderDesc(status:String) : List<ProductGroup>?
}