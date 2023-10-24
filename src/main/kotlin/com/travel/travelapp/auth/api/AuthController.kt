package com.travel.travelapp.auth.api

import com.travel.travelapp.auth.api.dto.SignInBody
import com.travel.travelapp.auth.api.dto.SignInResponse
import com.travel.travelapp.auth.api.dto.SignUpBody
import com.travel.travelapp.auth.service.AuthService
import com.travel.travelapp.security.AuthUser
import com.travel.travelapp.security.AuthUserData
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpBody: SignUpBody) = authService.signUp(signUpBody)

    @PostMapping("/signin")
    fun signIn(@RequestBody signInBody: SignInBody): SignInResponse=authService.signIn(signInBody)

    @GetMapping("/refresh")
    fun refresh(@Parameter(hidden = true) @AuthUser user: AuthUserData): AuthUserData{
        println("start")
        println(user)
        println("end")
        return user
    }
}