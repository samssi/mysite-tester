package com.samssi.mysite.authentication

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.samssi.mysite.JsonUtil
import com.samssi.mysite.TestContainerSpec
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.BehaviorSpec

internal class AuthenticationSpec : BehaviorSpec({
    given("Login attempt") {
        val unauthorizedDescription = "Unauthorized(401) and empty token returned"
        val authorizedDescription = "OK(200) and token returned"

        `when` ("empty username and password") {
            then(unauthorizedDescription) {
                val (response, token) = postAuth(emptyUserLogin)
                assertUnauthorized(response, token)
            }
        }

        `when` ("username correct but password incorrect") {
            then(unauthorizedDescription) {
                val (response, token) = postAuth(usernameCorrectPasswordIncorrect)
                assertUnauthorized(response, token)
            }
        }

        `when` ("username incorrect but password correct to some other users password") {
            then(unauthorizedDescription) {
                val (response, token) = postAuth(usernameInCorrectPasswordCorrectToSomeOtherUserPassword)
                assertUnauthorized(response, token)
            }
        }

        `when` ("username correct but password correct to some other users password") {
            then(unauthorizedDescription) {
                val (response, token) = postAuth(usernameCorrectPasswordCorrectToSomeOtherUser)
                assertUnauthorized(response, token)
            }
        }

        `when` ("username correct and password correct for foobar") {
            then(authorizedDescription) {
                val (response, token) = postAuth(usernameCorrectPasswordCorrectForFoobar)
                assertAuthorized(response, token)
            }
        }

        `when` ("username correct and password correct for baz") {
            then(authorizedDescription) {
                val (response, token) = postAuth(usernameCorrectPasswordCorrectForBaz)
                assertAuthorized(response, token)
            }
        }
    }
})

fun assertAuthorized(response: Response, token: String) {
    response.statusCode shouldBe 200
    token shouldNotBe ""
}

fun assertUnauthorized(response: Response, token: String) {
    response.statusCode shouldBe 401
    token shouldBe ""
}

fun postAuth(login: Login): Pair<Response, String> {
    val loginUrl = TestContainerSpec.authUrl("/api/v1/auth/login")

    val (_, response, _) = loginUrl.httpPost().jsonBody(com.google.gson.Gson().toJson(login)).response()
    val token = JsonUtil.jsonResponseAsType<Token>(response).token
    return Pair(response, token)
}
