package com.samssi.mysite.authentication

import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.samssi.mysite.JsonUtil.jsonResponseAsType
import com.samssi.mysite.MysiteAuthContainerSpec.authUrl
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class PasswordUtilSpec : BehaviorSpec({
    given("Password request") {
        val badRequestResponse = "Bad request(400) responded"
        val passwordUtilUrl = authUrl("/api/v1/auth/util/password")

        `when`("Invalid empty body provided") {
            then(badRequestResponse) {
                val (_, response, _ ) = passwordUtilUrl.httpPost().jsonBody("{}").response()
                response.statusCode shouldBe 400
            }
        }
        `when`("Invalid json body provided") {
            then(badRequestResponse) {
                val (_, response, _ ) = passwordUtilUrl.httpPost().jsonBody(Gson().toJson(Invalid("12345"))).response()
                response.statusCode shouldBe 400
            }
        }
        `when`("Valid password requested") {
            then("OK (200) and password and salt retuned with the length of 1024") {
                val (_, response, _ ) = passwordUtilUrl.httpPost().jsonBody(Gson().toJson(Password("12345"))).response()
                val passwordAndSalt = jsonResponseAsType<PasswordAndSalt>(response)
                val requiredPasswordOrSaltLength = 1024

                response.statusCode shouldBe 200
                passwordAndSalt.password.length shouldBe requiredPasswordOrSaltLength
                passwordAndSalt.salt.length shouldBe requiredPasswordOrSaltLength
            }
        }
    }
})
