package com.travel.travelapp.security

import com.travel.travelapp.common.exception.InvalidJwtTokenException
import jakarta.servlet.http.HttpServletRequest
import java.util.*


object AuthorizationExtractor {
    private const val AUTHENTICATION_TYPE = "Bearer"
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"
    private const val START_TOKEN_INDEX = 6
    fun extractAccessToken(request: HttpServletRequest): String {
        val headers = request.getHeaders(AUTHORIZATION_HEADER_KEY)
        while (headers.hasMoreElements()) {
            val value = headers.nextElement()
            if (value.lowercase(Locale.getDefault()).startsWith(AUTHENTICATION_TYPE.lowercase(Locale.getDefault()))) {
                val extractToken = value.trim { it <= ' ' }.substring(START_TOKEN_INDEX)
                validateExtractToken(extractToken)
                return extractToken
            }
        }
        throw InvalidJwtTokenException()
    }

    private fun validateExtractToken(extractToken: String) {
        if (extractToken.isBlank()) {
            throw InvalidJwtTokenException()
        }
    }
}

