package com.travel.travelapp.common.utils

import org.springframework.security.crypto.bcrypt.BCrypt

object BCryptUtils {

    fun encrypt(password: String) =
        BCrypt.hashpw(password,BCrypt.gensalt())

    fun verify(password: String, hashedPassword: String): Boolean =
        BCrypt.checkpw(password,hashedPassword)
}