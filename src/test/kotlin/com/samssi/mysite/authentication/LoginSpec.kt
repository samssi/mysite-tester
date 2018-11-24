package com.samssi.mysite.authentication

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.samssi.mysite.testcontainer.MysiteAuthTestContainer
import com.samssi.mysite.util.JsonUtil
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.BehaviorSpec

val loginUrl = MysiteAuthTestContainer.authUrl("/api/v1/auth/login")

internal class AuthenticationSpec : BehaviorSpec({
    given("Login attempt") {
        val unauthorizedDescription = "Unauthorized(401) and empty token returned"
        val authorizedDescription = "OK(200) and token returned"
        val badRequestDescription = "Bad request(400)"

        `when` ("invalid empty json body") {
            then(badRequestDescription) {
                val (_, response, _) = loginUrl.httpPost().jsonBody("{}").response()
                response.statusCode shouldBe 400
            }
        }

        `when` ("invalid json body") {
            then(badRequestDescription) {
                val (_, response, _) = loginUrl.httpPost().jsonBody(Gson().toJson(Invalid("bar"))).response()
                response.statusCode shouldBe 400
            }
        }

        `when` ("valid empty username and password") {
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

internal fun assertAuthorized(response: Response, token: String) {
    response.statusCode shouldBe 200
    token shouldNotBe ""
}

internal fun assertUnauthorized(response: Response, token: String) {
    response.statusCode shouldBe 401
    token shouldBe ""
}

internal fun postAuth(login: Login): Pair<Response, String> {
    val (_, response, _) = loginUrl.httpPost().jsonBody(Gson().toJson(login)).response()
    val token = JsonUtil.jsonResponseAsType<Token>(response).token
    return Pair(response, token)
}
