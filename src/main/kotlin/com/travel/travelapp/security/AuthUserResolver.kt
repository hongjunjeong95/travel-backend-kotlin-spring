package com.travel.travelapp.security

import com.travel.travelapp.common.interceptors.AuthUser
import com.travel.travelapp.user.persistent.User
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder

//@Aspect
//@Component
class AuthUserResolver(
    private val authenticationManager: AuthenticationManager
) {
//    @Around("@annotation(authUser)")
    fun processAuthUserParameter(joinPoint: ProceedingJoinPoint, authUser: AuthUser): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User // Change 'User' to your actual User class

        // You can perform additional checks or validations here if needed

        // Proceed with the original method invocation
        return joinPoint.proceed(arrayOf(user))
    }
}
