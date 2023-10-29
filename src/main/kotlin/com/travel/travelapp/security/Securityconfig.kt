package com.travel.travelapp.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val entryPoint: AuthenticationEntryPoint
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration?): AuthenticationManager? =
        authenticationConfiguration?.getAuthenticationManager()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authWhiteList = arrayOf(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/h2-console"
        )

        http
            .csrf { it.disable() }
            .headers { it.frameOptions { it.sameOrigin() } }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/signup","/api/v1/auth/signin").permitAll()
                    .requestMatchers(*authWhiteList).permitAll()
                    .anyRequest().authenticated()
            }
            .cors {
                it.configurationSource(corsConfigurationSource())
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:8080", "http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("Origin", "X-Requested-With", "Content-Type", "Authorization", "Oauth-Token")
        configuration.maxAge = 3000L
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun users(): UserDetailsService {
        val user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("1111"))
            .roles("USER")
            .build()
        val admin = User.builder()
            .username("a min")
            .password(passwordEncoder().encode("1111"))
            .roles("USER", "ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }
}