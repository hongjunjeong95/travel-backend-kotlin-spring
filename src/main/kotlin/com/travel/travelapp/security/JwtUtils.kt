package com.travel.travelapp.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.travel.travelapp.user.persistent.User
import java.time.Instant
import java.util.*


object JWTUtil {
    private val ALGORITHM: Algorithm = Algorithm.HMAC256("jongwon")
    private const val AUTH_TIME: Long = 2
    private const val REFRESH_TIME = (60 * 60 * 24 * 7).toLong()

    fun createAuthToken(claim: JWTClaim, properties: JWTProperties) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(claim.userId.toString())
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("email", claim.email)
            .withClaim("username", claim.username)
            .sign(Algorithm.HMAC256(properties.secret))

    fun makeRefreshToken(user: User): String {
        return JWT.create()
            .withSubject(user.email)
            .withClaim("exp", Instant.now().epochSecond + REFRESH_TIME)
            .sign(ALGORITHM)
    }

    fun verify(token: String?): VerifyResult =
         try {
            val decodedJwt = JWT.require(ALGORITHM).build().verify(token)
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
)
