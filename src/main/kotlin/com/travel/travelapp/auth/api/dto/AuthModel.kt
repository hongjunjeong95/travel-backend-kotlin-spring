package com.travel.travelapp.auth.api.dto

import com.travel.travelapp.user.persistent.UserRole

data class SignUpBody(
    val email: String,
    val password: String,
    val confirmPassword:String,
    val role: UserRole
)

data class SignInBody(
    val email: String,
    val password: String,
)

data class SignInResponse(
    val email: String,
    val authToken: String,
    val refreshToken: String,
)

data class RefreshResponse(
    val authToken: String,
)