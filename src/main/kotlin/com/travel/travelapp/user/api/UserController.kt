package com.travel.travelapp.user.api

import com.travel.travelapp.user.api.dto.SignInBody
import com.travel.travelapp.user.api.dto.SignInResponse
import com.travel.travelapp.user.api.dto.SignUpBody
import com.travel.travelapp.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpBody: SignUpBody) = userService.signUp(signUpBody)

    @PostMapping("/signin")
    fun signIn(@RequestBody signInBody: SignInBody): SignInResponse = userService.signIn(signInBody)
}