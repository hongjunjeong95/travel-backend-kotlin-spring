package com.travel.travelapp.common.interceptors

import com.travel.travelapp.security.AuthorizationExtractor
import com.travel.travelapp.security.TokenProvider
import jakarta.servlet.http.HttpServletRequest

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor


@Component
class LoginInterceptor(private val tokenProvider: TokenProvider): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (isPreflight(request) || isSwaggerRequest(request)) {
            return true
        }
        val token: String = AuthorizationExtractor.extractAccessToken(request)
        tokenProvider.verifyAuthToken(token)
        return true
    }

    private fun isSwaggerRequest(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return uri.contains("swagger") || uri.contains("api-docs") || uri.contains("webjars")
    }

    private fun isPreflight(request: HttpServletRequest): Boolean {
        return request.method == HttpMethod.OPTIONS.toString()
    }
}

