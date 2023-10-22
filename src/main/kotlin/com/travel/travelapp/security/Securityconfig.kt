package com.travel.travelapp.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


//@EnableWebSecurity(debug = true)
//@EnableMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        val loginFilter = JWTLoginFilter(
//            authenticationManager(
//                http.getSharedObject(
//                    AuthenticationConfiguration::class.java
//                )
//            ), userService, jwtProperties
//        )
//        val checkFilter = com.travelapp.travelapp.security.JWTCheckFilter(
//            authenticationManager(
//                http.getSharedObject(
//                    AuthenticationConfiguration::class.java
//                )
//            ),
//            userService,
//        )
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers("/admin.html").hasRole("ADMIN")
                    .requestMatchers("/","/admin.html", "/swagger-ui/**","/h2-console").permitAll()
                    .anyRequest().permitAll()
            }
            .formLogin {  }
            .logout {  }

//        http {
//            authorizeRequests {
//                authorize(anyRequest, authenticated)
//            }
//            formLogin { }
//            httpBasic { }
//        }
//        return http.build()

//        http
//            .csrf { it.disable() }
//            .formLogin { it.disable() }
//            .httpBasic { it.disable() }
//            .authorizeHttpRequests {
//                it
//                    .requestMatchers("/", "/swagger-ui/**","/h2-console").permitAll()
//                    .requestMatchers(PathRequest.toH2Console()).permitAll()
//                .anyRequest().permitAll()
//            }
//            .sessionManagement {
//                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .addFilterAt(checkFilter, BasicAuthenticationFilter::class.java)
//        http
//            .authorizeHttpRequests { auth ->
//                auth
//                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
//            }
//            .headers { headers -> headers.frameOptions().disable() }
//            .csrf { csrf ->
//                csrf
//                    .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
//            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
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