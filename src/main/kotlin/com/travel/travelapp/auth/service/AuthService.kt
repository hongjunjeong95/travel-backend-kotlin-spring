package com.travel.travelapp.auth.service

import com.travel.travelapp.auth.api.dto.RefreshResponse
import com.travel.travelapp.auth.api.dto.SignInBody
import com.travel.travelapp.auth.api.dto.SignInResponse
import com.travel.travelapp.auth.api.dto.SignUpBody
import com.travel.travelapp.common.exception.PasswordNotMatchException
import com.travel.travelapp.common.exception.PasswordNotMatchedException
import com.travel.travelapp.common.exception.UserExistsException
import com.travel.travelapp.common.exception.UserNotFoundException
import com.travel.travelapp.common.utils.BCryptUtils
import com.travel.travelapp.security.JWTClaim
import com.travel.travelapp.security.JwtUtils
import com.travel.travelapp.user.persistent.User
import com.travel.travelapp.user.persistent.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
) {

    @Transactional
    fun signUp(signUpBody: SignUpBody) {
        with(signUpBody){
            userRepository.findByEmail(email)?.let{
                throw UserExistsException()
            }
            if(password != confirmPassword) {
                throw PasswordNotMatchException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.encrypt(password),
                username = email,
            )

            userRepository.save(user)
        }
    }

    @Transactional()
    fun signIn(signInBody: SignInBody): SignInResponse {
        return with(userRepository.findByEmail(signInBody.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(signInBody.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id,
                email = email,
                username = username
            )

            // get tokens
            val authToken = jwtUtils.createAuthToken(jwtClaim)
            val refreshToken = jwtUtils.createRefreshToken(jwtClaim)

            // update refresh token on user
            currentHashedRefreshToken = refreshToken
            userRepository.save(this)

            SignInResponse(
                email = email,
                authToken = authToken,
                refreshToken = refreshToken
            )
        }
    }

    @Transactional()
    fun refresh(userId:Long): RefreshResponse {
        return with(userRepository.findById(userId).get()) {
            val jwtClaim = JWTClaim(
                userId = id,
                email = email,
                username = username
            )

            val authToken = jwtUtils.createAuthToken(jwtClaim)

            RefreshResponse(
                authToken = authToken,
            )
        }
    }
}