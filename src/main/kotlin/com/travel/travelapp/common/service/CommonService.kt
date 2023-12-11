package com.travel.travelapp.common.service

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.common.persistent.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class CommonService<T: BaseEntity>(
    private val repository: BaseRepository<T, Long>
) {
    open fun save(param: T) =
        repository.save(param)

    open fun findById(id: Long): T =
         repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    open fun findAll():List<T> = repository.findAll()

    open fun findMany(page:Int, size:Int): Page<T> = repository.findAll(
        PageRequest.of(
            page - 1,
            size,
            Sort.by("createdAt").descending().and(Sort.by("id"))
        )
    )

    open fun softDeleteById(id: Long) =
        repository.softDeleteById(id)
}