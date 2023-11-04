package com.travel.travelapp.security

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.travel.travelapp.user.persistent.UserRole
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(0)
@Component
class JwtAuthenticationFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = extractCookie(request, "Authorization")  ?: throw JWTDecodeException("Token is null")
            val authUser = parseAuthUser(token)
            UsernamePasswordAuthenticationToken(
                authUser, null, authUser.role
            )
                .apply { details = WebAuthenticationDetails(request) }
                .also { SecurityContextHolder.getContext().authentication = it }
        } catch (e: TokenExpiredException) {	// 변경
            reissueAccessToken(request, response, e)
        } catch (e: Exception) {
            request.setAttribute("exception", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractCookie(req: HttpServletRequest, cookieName: String): String? {
        for (c in req.cookies) {
            if (c.name == cookieName) {
                return c.value
            }
        }
        return null
    }

    private fun reissueAccessToken(request: HttpServletRequest, response: HttpServletResponse, exception: Exception) {
        try {
            val refreshToken = extractCookie(request, "Refresh-Token") ?: throw exception
            val oldAccessToken = extractCookie(request, "Authorization")!!
            tokenProvider.verifyRefreshToken(refreshToken, oldAccessToken)

            val jwtClaim:JWTClaim = parseJwtCalims(oldAccessToken)

            val newAccessToken = tokenProvider.createAuthToken(jwtClaim)
            val user = parseAuthUser(newAccessToken)
            UsernamePasswordAuthenticationToken.authenticated(user, newAccessToken, user.role)
                .apply { details = WebAuthenticationDetails(request) }
                .also { SecurityContextHolder.getContext().authentication = it }

            response.setHeader("New-Access-Token", newAccessToken)
        } catch (e: Exception) {
            request.setAttribute("exception", e)
        }
    }

    private fun parseJwtCalims(token:String): JWTClaim{
        val decodedJwt = tokenProvider.decodeAuthToken(token)
        val userId = decodedJwt.subject.toLong()
        val claims = decodedJwt.claims
        val email = claims["email"]?.asString() ?: ""
        val username = claims["username"]?.asString() ?: ""
        val role = claims["role"]?.asString() ?: "anonymous"

      return JWTClaim(
            userId = userId,
            email = email,
            username = username,
            role = UserRole.valueOf(role)
        )
    }

    private fun parseAuthUser(token: String):AuthUser{
        val decodedJwt = tokenProvider.verifyAuthToken(token)
        val userId = decodedJwt.subject.toLong()
        val claims = decodedJwt.claims
        val email = claims["email"]?.asString() ?: ""
        val username = claims["username"]?.asString() ?: ""
        val role = claims["role"]?.asString() ?: "anonymous"

        return AuthUser(
            id = userId,
            email = email,
            username = username,
            role = setOf(SimpleGrantedAuthority(role))
        )
    }
}