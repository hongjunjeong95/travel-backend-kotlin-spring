package com.travel.travelapp.common.exception

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.exceptions.SignatureVerificationException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException) : ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = ex.code, message = ex.message)
    }

    @ExceptionHandler(SignatureVerificationException::class)
    fun handleSignatureVerificationException(ex: SignatureVerificationException) : ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 401, message = "Token is not verified")
    }

    @ExceptionHandler(JWTDecodeException::class)
    fun handleJWTDecodeException(ex: JWTDecodeException) : ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 401, message = "Token is null")
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(ex: TokenExpiredException) : ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 401, message = "Token Expired Error")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) : ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 500, message = "Internal Server Error")
    }
}