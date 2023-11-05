package com.travel.travelapp.common.exception

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.exceptions.SignatureVerificationException
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }

        return ResponseEntity.status(ex.code).body(ErrorResponse(code = ex.code, message = ex.message))
    }

    @ExceptionHandler(SignatureVerificationException::class)
    fun handleSignatureVerificationException(ex: SignatureVerificationException): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(code = 401, message = "Token is not verified"))
    }

    @ExceptionHandler(JWTDecodeException::class)
    fun handleJWTDecodeException(ex: JWTDecodeException): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(code = 401, message = "Token is null"))
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(ex: TokenExpiredException): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(code = 401, message = "Token Expired Error"))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDBUKException(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(code = 400, message = "중복 데이터 허용 불가"))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error { ex.message }
        logger.error { ex }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(code = 500, message = "Internal Server Error"))
    }
}