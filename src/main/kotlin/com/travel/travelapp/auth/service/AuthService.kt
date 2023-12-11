package com.travel.travelapp.auth.service

import com.travel.travelapp.common.exception.PasswordNotMatchException
import com.travel.travelapp.common.exception.PasswordNotMatchedException
import com.travel.travelapp.common.exception.UserExistsException
import com.travel.travelapp.common.exception.UserNotFoundException
import com.travel.travelapp.common.utils.BCryptUtils
import com.travel.travelapp.security.JWTClaim
import com.travel.travelapp.security.TokenProvider
import com.travel.travelapp.domain.user.persistent.User
import com.travel.travelapp.domain.user.persistent.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {

    @Transactional
    fun signUp(param: SignUpParam) {
        with(param){
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
                role = role
            )

            userRepository.save(user)
        }
    }

    @Transactional()
    fun signIn(param: SignInParam): SignInReturn {
        return with(userRepository.findByEmail(param.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(param.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id,
                email = email,
                username = username,
                role = role
            )

            val authToken = tokenProvider.createAuthToken(jwtClaim)
            val refreshToken = tokenProvider.createRefreshToken(jwtClaim)

            currentHashedRefreshToken = BCryptUtils.encrypt(refreshToken)
            userRepository.save(this)

            SignInReturn(
                authToken = authToken,
                refreshToken = refreshToken
            )
        }
    }
}