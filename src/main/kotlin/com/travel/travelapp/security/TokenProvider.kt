package com.travel.travelapp.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.travel.travelapp.user.persistent.UserRole
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider(
    private val jwtProperties: JWTProperties
) {
    private val AUTH_ALGORITHM = Algorithm.HMAC256(jwtProperties.authSecret)
    private val REFRESH_ALGORITHM = Algorithm.HMAC256(jwtProperties.refreshSecret)

    fun createAuthToken(claim: JWTClaim) =
        JWT.create()
            .withIssuer(jwtProperties.issuer)
            .withSubject(claim.userId.toString())
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + jwtProperties.authExpiresTime * 1000))
            .withClaim("email", claim.email)
            .withClaim("username", claim.username)
            .withClaim("role", claim.role.toString())
            .sign(AUTH_ALGORITHM)

    fun createRefreshToken(claim: JWTClaim): String {
        return JWT.create()
            .withIssuer(jwtProperties.issuer)
            .withSubject(claim.userId.toString())
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + jwtProperties.refreshExpiresTime * 1000))
            .withClaim("email", claim.email)
            .withClaim("username", claim.username)
            .sign(REFRESH_ALGORITHM)
    }

    fun verifyAuthToken(token: String?): VerifyResult =
        try {
            val decodedJwt = JWT.require(AUTH_ALGORITHM).build().verify(token)
            VerifyResult(true, decodedJwt)
        } catch (ex: Exception) {
            val decodedJwt = JWT.decode(token)
            VerifyResult(false, decodedJwt)
        }

    fun verifyRefreshToken(token: String?): VerifyResult =
        try {
            val decodedJwt = JWT.require(REFRESH_ALGORITHM).build().verify(token)
            VerifyResult(true, decodedJwt)
        } catch (ex: Exception) {
            val decodedJwt = JWT.decode(token)
            VerifyResult(false, decodedJwt)
        }
}

data class JWTClaim(
    val userId: Long,
    val email: String,
    val username: String,
    val role: UserRole
)