package com.travel.travelapp.auth.api.dto

data class SignUpBody(
    val email: String,
    val password: String,
    val confirmPassword:String,
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