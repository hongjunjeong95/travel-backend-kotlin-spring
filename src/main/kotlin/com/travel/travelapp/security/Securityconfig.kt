package com.travel.travelapp.security

import com.travel.travelapp.user.service.UserService
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
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfig(
    private val userService: UserService,
    private val jwtProperties: JWTProperties
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration?): AuthenticationManager? =
        authenticationConfiguration?.getAuthenticationManager()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val loginFilter = JWTLoginFilter(
            authenticationManager(
                http.getSharedObject(
                    AuthenticationConfiguration::class.java
                )
            ), userService, jwtProperties
        )
        val checkFilter = JWTCheckFilter(
            authenticationManager(
                http.getSharedObject(
                    AuthenticationConfiguration::class.java
                )
            ),
            userService,
        )
//        http
//            .csrf { it.disable() }
//            .authorizeHttpRequests {
//                it
//                    .requestMatchers("/admin.html").hasRole("ADMIN")
//                    .requestMatchers("/","/admin.html", "/swagger-ui/**","/h2-console").permitAll()
//                    .anyRequest().authenticated()
//            }
//            .formLogin {  }
//            .logout {  }
//            .sessionManagement {  }
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/admin.html").permitAll()
                    .requestMatchers("/","/admin.html", "/swagger-ui/**","/h2-console").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .formLogin {  }
//            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .addFilterAt(checkFilter, BasicAuthenticationFilter::class.java)

        return http.build()
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