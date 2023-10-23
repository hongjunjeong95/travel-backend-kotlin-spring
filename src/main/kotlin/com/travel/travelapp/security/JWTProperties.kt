package com.travel.travelapp.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTProperties(
    val issuer: String,
    val authSecret: String,
    val authExpiresTime: Long,
    val refreshSecret: String,
    val refreshExpiresTime: Long,
)