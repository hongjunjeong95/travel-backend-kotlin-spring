package com.travel.travelapp

import com.travel.travelapp.security.JWTClaim
import com.travel.travelapp.security.JWTProperties
import com.travel.travelapp.security.JWTUtil
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JWTUtilsTest {

    private val logger = KotlinLogging.logger {}

    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            username = "개발자",
        )

        val properties = JWTProperties(
            issuer = "travelapp",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtil.createAuthToken(jwtClaim, properties)

        assertNotNull(token)

        logger.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            username = "개발자",
        )

        val properties = JWTProperties(
            issuer = "jara",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtil.createAuthToken(jwtClaim, properties)

        val verifyResult = JWTUtil.verify(token)

        with(verifyResult.decodedJwt){
            logger.info { "claims : $claims" }

            val userId = subject.toLong()
            assertEquals(userId, jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertEquals(email, jwtClaim.email)

            val username = claims["username"]!!.asString()
            assertEquals(username, jwtClaim.username)
        }

//        with(decode) {
//            logger.info { "claims : $claims" }
//
//            val userId = claims["userId"]!!.asLong()
//            assertEquals(userId, jwtClaim.userId)
//
//            val email = claims["email"]!!.asString()
//            assertEquals(email, jwtClaim.email)
//
//            val username = claims["username"]!!.asString()
//            assertEquals(username, jwtClaim.username)
//        }

    }
}