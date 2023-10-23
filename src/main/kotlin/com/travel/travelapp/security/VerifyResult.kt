package com.travel.travelapp.security

import com.auth0.jwt.interfaces.DecodedJWT

data class VerifyResult (
    val success:Boolean,
    val decodedJwt:DecodedJWT
)