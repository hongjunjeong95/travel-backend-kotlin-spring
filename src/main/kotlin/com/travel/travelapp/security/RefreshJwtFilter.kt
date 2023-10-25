package com.travel.travelapp.security

import com.auth0.jwt.interfaces.DecodedJWT
import com.travel.travelapp.common.utils.BCryptUtils
import com.travel.travelapp.user.persistent.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class RefreshJwtFilter(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    private val jwtSecret = "your-secret-key"

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtFilter::class.java)
        const val AUTHORIZATION_HEADER = "Authorization"
        const val REFRESH_HEADER = "Refresh"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authToken = extractJwtFromRequest(request, AUTHORIZATION_HEADER)
        val refreshToken = extractJwtFromRequest(request, REFRESH_HEADER)

        if (authToken != null && refreshToken != null) {
            val verifyResult = jwtUtils.verifyAuthToken(authToken)
            val userId = verifyResult.decodedJwt.subject.toLong()
            val user = userRepository.findById(userId).orElseThrow()

            if (user.currentHashedRefreshToken != null && isRefreshTokenValid(user, refreshToken)) {
                val authUser = createAuthUser(verifyResult.decodedJwt)
                val userToken = createUsernamePasswordAuthenticationToken(authUser)
                SecurityContextHolder.getContext().authentication = userToken
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest, header:String): String? {
        val authorizationHeader = request.getHeader(header)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring("Bearer ".length)
        } else null
    }

    private fun isRefreshTokenValid(user: com.travel.travelapp.user.persistent.User, refreshToken: String): Boolean {
        return BCryptUtils.verify(user.currentHashedRefreshToken!!, refreshToken)
    }

    private fun createAuthUser(decodedJWT: DecodedJWT): AuthUser {
        val email = decodedJWT.claims["email"]?.asString() ?: ""
        val username = decodedJWT.claims["username"]?.asString() ?: ""
        return AuthUser(id = decodedJWT.subject.toLong(), email = email, username = username)
    }

    private fun createUsernamePasswordAuthenticationToken(authUser: AuthUser): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(authUser, null, AuthorityUtils.createAuthorityList())
    }
}
