package com.travel.travelapp.domain.user.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface UserRepository: BaseRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String) : User?
}