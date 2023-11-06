package com.travel.travelapp.productGroup.api

import com.travel.travelapp.product.service.ProductService
import com.travel.travelapp.productGroup.api.dto.AddProductToGroupBody
import com.travel.travelapp.productGroup.api.dto.CreateProductGroupBody
import com.travel.travelapp.productGroup.persistent.ProductGroup
import com.travel.travelapp.productGroup.persistent.ProductGroupStatus
import com.travel.travelapp.productGroup.service.ProductGroupService
import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import com.travel.travelapp.productGroupList.service.ProductGroupListService
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
        productGroupService.create(
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
            productGroupListService.create(
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