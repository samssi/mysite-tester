package com.samssi.mysite.content

import com.github.kittinunf.fuel.httpGet
import com.samssi.mysite.JsonUtil.jsonResponseAsType
import com.samssi.mysite.authentication.Message
import com.samssi.mysite.testcontainer.MysiteRestTestContainer
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class AboutSpec : BehaviorSpec({
    val contentUrl = MysiteRestTestContainer.contentUrl("/api/v1/content/private/contents/about")
    given("Document about requested") {
        `when`("without valid JWT token") {
            then("""Forbidden(403) and message "No token provided."""") {
                val (_ ,response, _)= contentUrl.httpGet().response()
                val message = jsonResponseAsType<Message>(response)
                response.statusCode shouldBe 403
                message.message shouldBe "No token provided."
            }
        }
    }
})