package com.travelapp.travelapp.user.persistent

import com.travel.travelapp.user.persistent.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String) : User?
}