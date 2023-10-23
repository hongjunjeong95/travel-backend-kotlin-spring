package com.travel.travelapp.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.travel.travelapp.auth.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.time.ZoneId

class JwtFilter(private val jwtUtils: JwtUtils,
                private val userService: UserService
) : OncePerRequestFilter() {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtFilter::class.java)
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    // 실제 필터릴 로직
    // 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)
        if(token == null){
            filterChain.doFilter(request,response)
            return
        }
        val requestURI = request.requestURI
        val result = jwtUtils.verifyAuthToken(token)

        if (result.success) {
            val user = userService.loadUserByUsername(result.decodedJwt.claims["username"].toString())
            val userToken = UsernamePasswordAuthenticationToken(
                user.username, null, user.authorities
            )
            SecurityContextHolder.getContext().authentication = userToken
            logger.info("Security Context에 '${userToken.name}' 인증 정보를 저장했습니다, uri: $requestURI")
        } else {
            logger.info("유효한 JWT 토큰이 없습니다, uri: $requestURI")
            val expiredOn = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            throw TokenExpiredException("Token is not valid",expiredOn)
        }

        filterChain.doFilter(request, response)
    }

    // Request Header에서 토큰 정보를 꺼내오기 위한 메소드
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length)
        }
        return null
    }
}
