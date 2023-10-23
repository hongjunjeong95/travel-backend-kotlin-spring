package com.travel.travelapp.user.persistent

import com.travel.travelapp.common.persistent.BaseRepository

interface UserRepository: BaseRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String) : User?
}