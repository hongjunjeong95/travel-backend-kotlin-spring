package com.travel.travelapp.domain.productGroupList.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface ProductGroupListRepository: BaseRepository<ProductGroupList, Long>{
    fun findByProductGroupId(productGroupId : Long) : List<ProductGroupList>?
}