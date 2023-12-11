package com.travel.travelapp.domain.productGroup.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface ProductGroupRepository: BaseRepository<ProductGroup, Long>{
    fun findByStatusOrderByOrderDesc(status:String) : List<ProductGroup>?
}