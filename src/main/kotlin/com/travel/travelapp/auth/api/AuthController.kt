package com.travel.travelapp.auth.api

import com.travel.travelapp.auth.api.dto.SignInBody
import com.travel.travelapp.auth.api.dto.SignInResponse
import com.travel.travelapp.auth.api.dto.SignUpBody
import com.travel.travelapp.auth.service.AuthService
import com.travel.travelapp.common.interceptors.AuthUser
//import com.travel.travelapp.security.AuthUser
import com.travel.travelapp.user.persistent.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpBody: SignUpBody) = authService.signUp(signUpBody)

    @PostMapping("/signin")
    fun signIn(@RequestBody signInBody: SignInBody, user: AuthUser): SignInResponse{
        println(user)
        return authService.signIn(signInBody)
    }

    @GetMapping("/refresh")
    fun refresh(@RequestBody signInBody: SignInBody, user: AuthUser): SignInResponse{
        println(user)
        return authService.refresh(signInBody)
    }
}