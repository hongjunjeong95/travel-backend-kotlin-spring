package com.travel.travelapp.user.persistent

import com.travel.travelapp.common.persistent.BaseEntity
import jakarta.persistence.*

//import org.springframework.data.annotation.Id
//import org.springframework.data.relational.core.mapping.Column
//import org.springframework.data.relational.core.mapping.Table

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
): BaseEntity()