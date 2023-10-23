package com.travel.travelapp.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.travel.travelapp.security.JwtUtils.verify
import com.travel.travelapp.user.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId

class JWTCheckFilter(private val authenticationManager: AuthenticationManager?, private val userService: UserService) :
    BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val bearer = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }
        val token = bearer.substring("Bearer ".length)
        val result = verify(token)
        if (result.success) {
            val user = userService.loadUserByUsername(result.decodedJwt.claims["username"].toString())
            val userToken = UsernamePasswordAuthenticationToken(
                user.username, null, user.authorities
            )
            SecurityContextHolder.getContext().authentication = userToken
            chain.doFilter(request, response)
        } else {
            val expiredOn = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            throw TokenExpiredException("Token is not valid",expiredOn)
        }
    }
}
