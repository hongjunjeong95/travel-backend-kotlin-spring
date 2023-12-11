package com.travel.travelapp.domain.product.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.productGroupList.persistent.ProductGroupList
import com.vladmihalcea.hibernate.type.json.JsonType
import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "product")
class Product(
    @Column(name = "title")
    var title: String,

    @Column(name = "price")
    var price: Int,

    @Column(name = "image_url")
    var imageUrl: String,

    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    var details: MutableList<ProductDetails> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val productGroupList:  List<ProductGroupList> = emptyList()
): BaseEntity(){
    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Product::id)
        private val toStringProperties = arrayOf(
            Product::id,
        )
    }
}

data class ProductDetails(
    @Schema(description = "헤더", example = "헤더 예제")
    var header: String,

    @Schema(description = "순서", example = "1")
    var order: Int,

    @Schema(description = "내용 (예제)", example = "{\"key1\": \"value1\", \"key2\": \"value2\"}")
    var content: Map<String, Any?>?
)