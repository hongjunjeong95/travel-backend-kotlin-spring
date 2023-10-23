package com.travel.travelapp.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.fasterxml.jackson.databind.ObjectMapper
import com.travel.travelapp.security.JWTUtil.makeRefreshToken
import com.travel.travelapp.security.JWTUtil.verify
import com.travel.travelapp.user.api.dto.SignUpBody
import com.travel.travelapp.user.persistent.User
import com.travel.travelapp.user.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId


class JWTLoginFilter(
    private val authenticationManager: AuthenticationManager?,
    private val userService: UserService,
    private val jwtProperties: JWTProperties
) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {
    private val objectMapper = ObjectMapper()

    init {
        setFilterProcessesUrl("/login")
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val refresh = request.getHeader("refresh_token")
        val userLogin: SignUpBody = objectMapper.readValue(request.inputStream, SignUpBody::class.java)
        return if (refresh == null) {
            val token = UsernamePasswordAuthenticationToken(
                userLogin.email, userLogin.password, null
            )
            // user details...
            authenticationManager!!.authenticate(token)
        } else {
            val result =  verify(refresh)
            if (result.success) {
                val user: UserDetails = userService.loadUserByUsername(result.decodedJwt.claims["username"].toString())
                UsernamePasswordAuthenticationToken(
                    user, user.authorities
                )
            } else {
                val expiredOn = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
                throw TokenExpiredException("refresh token expired",expiredOn)
            }
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user: User = authResult.principal as User
        val jwtClaim = JWTClaim(
            userId = user.id,
            email = user.email,
            username = user.username
        )
        response.setHeader("auth_token", JWTUtil.createAuthToken(jwtClaim, jwtProperties))
        response.setHeader("refresh_token", makeRefreshToken(user))
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.outputStream.write(objectMapper.writeValueAsBytes(user))
    }
}

