package com.samssi.mysite.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.samssi.mysite.authentication.usernameCorrectPasswordCorrectForBaz

internal object JwtFixture {
    private val algorithm = Algorithm.HMAC256("FAULTY SECRET")

    internal fun faultySecretSignedJwtToken(): String {
        return JWT.create()
            .withIssuer("MySite")
            .withClaim("sub", usernameCorrectPasswordCorrectForBaz.username)
                .sign(algorithm)
    }
}