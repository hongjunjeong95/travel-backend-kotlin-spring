package com.travel.travelapp.security

import com.travel.travelapp.user.persistent.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@Order(1)
class RefreshSecurityconfig(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/v1/auth/refresh").authenticated()
            }
            .addFilterAt(RefreshJwtFilter(jwtUtils,userRepository),UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}