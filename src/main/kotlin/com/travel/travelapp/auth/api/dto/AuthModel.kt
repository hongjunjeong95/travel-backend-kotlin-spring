package com.travel.travelapp.auth.api.dto

import com.travel.travelapp.user.persistent.UserRole
import io.swagger.v3.oas.annotations.media.Schema


data class SignUpBody(
    @Schema(description = "email", example = "hello@gmail.com")
    val email: String,

    @Schema(description = "password", example = "1234")
    val password: String,

    @Schema(description = "confirm password", example = "1234")
    val confirmPassword:String,

    @Schema(description = "User Role", example = "PRODUCER")
    val role: UserRole
)

data class SignInBody(
    @Schema(description = "email", example = "hello@gmail.com")
    val email: String,

    @Schema(description = "password", example = "1234")
    val password: String,
)