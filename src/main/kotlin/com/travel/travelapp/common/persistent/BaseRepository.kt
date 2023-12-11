package com.travel.travelapp.common.persistent

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import java.io.Serializable

@NoRepositoryBean
interface BaseRepository<T : BaseEntity, ID : Serializable> : JpaRepository<T, ID> {
    @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    @Modifying
    fun softDeleteById(@Param("id") id: ID)

    @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e IN :entities")
    @Modifying
    fun softDeleteAll(@Param("entities") entities: MutableIterable<T>)
}