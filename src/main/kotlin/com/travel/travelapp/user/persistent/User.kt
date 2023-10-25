package com.travel.travelapp.user.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

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
): BaseEntity()

enum class UserRole {
    USER, ADMIN, ANONYMOUS
}