package com.travel.travelapp.domain.user.persistent

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.travel.travelapp.common.persistent.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
class User(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column
    var username: String,

    @Column(name = "current_hashed_refresh_token", nullable = true)
    var currentHashedRefreshToken: String? = null,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER
): BaseEntity(){
    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(User::id)
        private val toStringProperties = arrayOf(
            User::id,
        )
    }
}


enum class UserRole {
    USER, PRODUCER, ADMIN, ANONYMOUS
}