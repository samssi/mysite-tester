package com.samssi.mysite.content

import com.github.kittinunf.fuel.httpGet
import com.samssi.mysite.testcontainer.MysiteRestTestContainer
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class PublicContentSpec: BehaviorSpec({
    val availableImageUrl = "/api/v1/content/public/static/images/c64-me-small.png"
    val publicContentUrl = MysiteRestTestContainer.contentUrl(availableImageUrl)

    given("User calls for image content api public route $publicContentUrl") {
        `when`("available image") {
            then("200 (OK) and corresponding image gets returned") {
                val (_, response, _) = publicContentUrl.httpGet().response()

                response.statusCode shouldBe 200
                response.headers["Content-Type"] shouldBe listOf("image/png")
            }
        }
    }

})