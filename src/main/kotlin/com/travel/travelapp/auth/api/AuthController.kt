package com.travel.travelapp.auth.api

import com.travel.travelapp.auth.api.dto.SignInBody
import com.travel.travelapp.auth.api.dto.SignUpBody
import com.travel.travelapp.auth.service.AuthService
import com.travel.travelapp.auth.service.SignInParam
import com.travel.travelapp.auth.service.SignUpParam
import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.security.JWTProperties
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService,private val jwtProperties: JWTProperties) {
    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody signUpBody: SignUpBody):ApiResponse<Unit> =
        ApiResponse.success(authService.signUp(
            with(signUpBody){
                SignUpParam(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    role = role
                )
            }
        ))


    @Operation(summary = "로그인")
    @PostMapping("/signin")
    fun signIn(@RequestBody signInBody: SignInBody, response: HttpServletResponse): ResponseEntity<ApiResponse<Unit>> {
        val result = authService.signIn(with(signInBody){
            SignInParam(
                email = email,
                password = password
            )
        })
        val authCookie = ResponseCookie.from("Authorization", result.authToken)
            .maxAge(jwtProperties.authExpiresTime)
            .httpOnly(true)
            .path("/")
            .build()

        val refreshCookie = ResponseCookie.from("Refresh-Token", result.refreshToken)
            .maxAge(jwtProperties.refreshExpiresTime)
            .httpOnly(true)
            .path("/")
            .build()

        response.setHeader(HttpHeaders.SET_COOKIE, authCookie.toString())
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString())

        return ResponseEntity.ok()
            .body(ApiResponse.success(Unit))
    }

}