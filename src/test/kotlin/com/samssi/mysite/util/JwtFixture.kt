package com.samssi.mysite.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.samssi.mysite.authentication.usernameCorrectPasswordCorrectForBaz
import java.util.*

internal object JwtFixture {
    private val faultySecret = Algorithm.HMAC256("FAULTY SECRET")
    private val actualSecret = Algorithm.HMAC256("very secret")

    internal fun faultySecretSignedJwtToken(): String {
        return JWT.create()
            .withIssuer("MySite")
            .withClaim("sub", usernameCorrectPasswordCorrectForBaz.username)
                .sign(faultySecret)
    }

    internal fun expiredSecretSignedJwtToken(): String {
        return JWT.create()
            .withIssuer("MySite")
            .withClaim("sub", usernameCorrectPasswordCorrectForBaz.username)
            .withClaim("iat", Date(1540000000))
            .withClaim("exp", Date(1540000001))
            .sign(actualSecret)
    }
}