package com.travel.travelapp.common.service

import com.travel.travelapp.common.persistent.BaseEntity
import com.travel.travelapp.common.persistent.BaseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class CommonService<T: BaseEntity>(
    private val repository: BaseRepository<T, Long>
) {
    open fun findById(id: Long): T =
         repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    open fun findAll():List<T> = repository.findAll()
}