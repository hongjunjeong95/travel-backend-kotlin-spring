package com.travel.travelapp

import com.travel.travelapp.security.JWTClaim
import com.travel.travelapp.security.JWTProperties
import com.travel.travelapp.security.TokenProvider
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import com.ninjasquad.springmockk.MockkBean
import com.travel.travelapp.user.persistent.UserRepository
import com.travel.travelapp.user.persistent.UserRole
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class TokenProviderTest {
    private val logger = KotlinLogging.logger {}
    @MockkBean lateinit var userRepository: UserRepository
    private lateinit var tokenProvider: TokenProvider

    private val properties = JWTProperties(
        issuer = "travelapp",
        authExpiresTime = 3600,
        authSecret = "asldkfjsalkdj",
        refreshExpiresTime = 3600,
        refreshSecret = "aslkdjaslkdf",
    )

    @BeforeEach
    fun setUp() {
        tokenProvider = TokenProvider(properties, userRepository)
    }


    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            username = "개발자",
            role = UserRole.PRODUCER
        )

        val token = tokenProvider.createAuthToken(jwtClaim)

        assertNotNull(token)

        logger.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            username = "개발자",
            role = UserRole.PRODUCER
        )

        val token = tokenProvider.createAuthToken(jwtClaim)

        val decodedJwt = tokenProvider.verifyAuthToken(token)

        with(decodedJwt){
            logger.info { "claims : $claims" }

            val userId = subject.toLong()
            assertEquals(userId, jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertEquals(email, jwtClaim.email)

            val username = claims["username"]!!.asString()
            assertEquals(username, jwtClaim.username)
        }
    }
}