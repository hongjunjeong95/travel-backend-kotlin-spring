package com.travel.travelapp.domain.productGroup.api

import com.travel.travelapp.domain.product.service.ProductService
import com.travel.travelapp.domain.productGroup.api.dto.AddProductToGroupBody
import com.travel.travelapp.domain.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.domain.productGroup.persistent.ProductGroup
import com.travel.travelapp.domain.productGroup.persistent.ProductGroupStatus
import com.travel.travelapp.domain.productGroup.service.ProductGroupService
import com.travel.travelapp.domain.productGroupList.persistent.ProductGroupList
import com.travel.travelapp.domain.productGroupList.service.ProductGroupListService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductGroupFacade(
    private val productGroupService: ProductGroupService,
    private val productService: ProductService,
    private val productGroupListService: ProductGroupListService,
) {
    @Transactional
    fun create(body: CreateProductGroupBody) {
        productGroupService.save(
            with(body){
                ProductGroup(
                    title = title,
                    order = order,
                )
            }
        )
    }

    @Transactional
    fun addProductToGroup(id:Long, body: AddProductToGroupBody) {
        val product = productService.findById(id)
        val productGroup = productGroupService.findById(id)

        try {
            productGroupListService.save(
                ProductGroupList(
                    product = product,
                    productGroup = productGroup
                )
            )
        } catch (e: DataIntegrityViolationException){
            throw DataIntegrityViolationException("중복 데이터 허용 불가2")
        } catch (e: DataIntegrityViolationException) {
            throw Exception("Server error")
        }
    }

    @Transactional
    fun findAll(): List<ProductGroup> = productGroupService.findByStatusOrderByOrderDesc(status = ProductGroupStatus.visible)
}