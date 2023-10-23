package com.travel.travelapp.user.service

import com.travel.travelapp.common.exception.PasswordNotMatchException
import com.travel.travelapp.common.exception.PasswordNotMatchedException
import com.travel.travelapp.common.exception.UserExistsException
import com.travel.travelapp.common.exception.UserNotFoundException
import com.travel.travelapp.common.utils.BCryptUtils
import com.travel.travelapp.security.JWTClaim
import com.travel.travelapp.security.JWTProperties
import com.travel.travelapp.security.JWTUtil
import com.travel.travelapp.user.api.dto.SignInBody
import com.travel.travelapp.user.api.dto.SignInResponse
import com.travel.travelapp.user.api.dto.SignUpBody
import com.travel.travelapp.user.persistent.User
import com.travel.travelapp.user.persistent.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
): UserDetailsService {

    @Transactional
    suspend fun signUp(signUpBody: SignUpBody) {
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


    @Transactional(readOnly = true)
    suspend fun signIn(signInBody: SignInBody): SignInResponse {
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

            val token = JWTUtil.createAuthToken(jwtClaim, jwtProperties)

            SignInResponse(
                email = email,
                token = token
            )
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
            val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("존재하지 않는 username 입니다.")
            return UserDetailsImplementation(user)
    }
}