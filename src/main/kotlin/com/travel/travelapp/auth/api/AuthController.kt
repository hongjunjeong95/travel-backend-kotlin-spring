package com.travel.travelapp.auth.api

import com.travel.travelapp.auth.api.dto.SignInBody
import com.travel.travelapp.auth.api.dto.SignInResponse
import com.travel.travelapp.auth.api.dto.SignUpBody
import com.travel.travelapp.auth.service.AuthService
import com.travel.travelapp.common.dto.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpBody: SignUpBody):ApiResponse<Unit> =
        ApiResponse.success(authService.signUp(signUpBody))


    @Operation(summary = "로그인")
    @PostMapping("/signin")
    fun signIn(@RequestBody signInBody: SignInBody): ApiResponse<SignInResponse> =
        ApiResponse.success(authService.signIn(signInBody))
}