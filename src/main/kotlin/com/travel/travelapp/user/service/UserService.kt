package com.travel.travelapp.user.service

import com.travel.travelapp.common.persistent.findByIdOrThrow
import com.travel.travelapp.user.persistent.User
import com.travel.travelapp.user.persistent.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun me(id: Long):User = userRepository.findByIdOrThrow(id, "존재하지 않는 회원입니다.")
}