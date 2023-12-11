package com.travel.travelapp.auth.api.dto

import com.travel.travelapp.domain.user.persistent.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
//import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class SignUpBody(
    @Schema(description = "email", example = "hello@gmail.com")
    @field:NotEmpty
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,

    @Schema(description = "password", example = "12345678")
    @field:Size(min = 8, max = 16, message = "비번은 최소 8자리 이상 16자리 이하입니다.")
    val password: String,

    @Schema(description = "confirm password", example = "12345678")
    @field:Size(min = 8, max = 16, message = "확인 비번은 최소 8자리 이상 16자리 이하입니다.")
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