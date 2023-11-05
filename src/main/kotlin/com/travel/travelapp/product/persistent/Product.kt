package com.travel.travelapp.product.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import com.vladmihalcea.hibernate.type.json.JsonType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "product")
data class Product(
    @Column(name = "title")
    var title: String,

    @Column(name = "price")
    var price: Int,

    @Column(name = "image_url")
    var imageUrl: String,

    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    var details: List<ProductDetails>,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val productGroupList:  List<ProductGroupList> = emptyList()

//    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "books", joinColumns = @JoinColumn(name = "library_id"))
//    @Column(name = "book", nullable = false)
//    private List<String> books = new ArrayList<>(),

//    @ElementCollection(targetClass = ProductDetails.class)
//    @CollectionTable(name = "details", joinColumns = [JoinColumn(name = "product_id")])
//    @Column(name = "detail")
//    var roles: List<ProductDetails>
): BaseEntity()

data class ProductDetails(
    @Schema(description = "헤더", example = "헤더 예제")
    var header: String,

    @Schema(description = "순서", example = "1")
    var order: Int,

    @Schema(description = "내용 (예제)", example = "{\"key1\": \"value1\", \"key2\": \"value2\"}")
    var content: Map<String, Any?>?
)