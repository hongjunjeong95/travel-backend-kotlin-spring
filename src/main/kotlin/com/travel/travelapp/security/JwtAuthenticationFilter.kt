package com.travel.travelapp.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.travel.travelapp.user.persistent.UserRole
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.time.ZoneId

@Order(0)
@Component
class JwtAuthenticationFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = parseBearerToken(request)
            val result = tokenProvider.verifyAuthToken(token)

            if (result.success) {
                val userId = result.decodedJwt.subject.toLong()
                val claims = result.decodedJwt.claims
                val email = claims["email"]?.asString() ?: ""
                val username = claims["username"]?.asString() ?: ""
                val role = claims["role"]?.asString() ?: "anonymous"

                val authUser = AuthUser(
                    id = userId,
                    email = email,
                    username = username,
                    role = setOf(SimpleGrantedAuthority(role))
                )
                UsernamePasswordAuthenticationToken(
                    authUser, null, authUser.role
                )
                    .apply { details = WebAuthenticationDetails(request) }
                    .also { SecurityContextHolder.getContext().authentication = it }
            } else {
                val expiredOn = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
                throw TokenExpiredException("Token is not valid",expiredOn)
            }
        } catch (e: Exception) {
            request.setAttribute("exception", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun parseBearerToken(request: HttpServletRequest) = request.getHeader(HttpHeaders.AUTHORIZATION)
        .takeIf { it?.startsWith("Bearer ", true) ?: false }?.substring(7)
}