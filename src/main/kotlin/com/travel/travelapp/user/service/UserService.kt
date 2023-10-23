package com.travel.travelapp.auth.service

import com.travel.travelapp.user.persistent.User
import com.travel.travelapp.user.persistent.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
            val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("존재하지 않는 username 입니다.")
            return UserDetailsImplementation(user)
    }
}