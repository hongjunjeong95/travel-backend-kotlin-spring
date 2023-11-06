package com.travel.travelapp.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.travel.travelapp.common.utils.BCryptUtils
import com.travel.travelapp.user.persistent.UserRepository
import com.travel.travelapp.user.persistent.UserRole
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider(
    private val jwtProperties: JWTProperties,
    private val userRepository: UserRepository
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
            .sign(AUTH_ALGORITHM)!!

    fun createRefreshToken(claim: JWTClaim): String {
        return JWT.create()
            .withIssuer(jwtProperties.issuer)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + jwtProperties.refreshExpiresTime * 1000))
            .sign(REFRESH_ALGORITHM)
    }

    fun verifyAuthToken(token: String): DecodedJWT =
        JWT.require(AUTH_ALGORITHM).build().verify(token)

    fun decodeAuthToken(token: String): DecodedJWT =
        JWT.decode(token)

    fun verifyRefreshToken(refreshToken: String, oldAuthToken:String):Boolean {
        JWT.require(REFRESH_ALGORITHM).build().verify(refreshToken)

        val userId = decodeAuthToken(oldAuthToken).subject.toLong()
        val currentHashedRefreshToken = userRepository.findById(userId).get().currentHashedRefreshToken
        return BCryptUtils.verify(refreshToken, currentHashedRefreshToken!!)
    }
}

data class JWTClaim(
    val userId: Long,
    val email: String,
    val username: String,
    val role: UserRole
)