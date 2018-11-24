package com.samssi.mysite.health

import com.github.kittinunf.fuel.httpGet
import com.samssi.mysite.testcontainer.MysiteAuthTestContainer
import com.samssi.mysite.testcontainer.MysiteRestTestContainer
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class HealthCheckSpec : BehaviorSpec({
    val healthCheckUrl = "/health-check"
    val mysiteRestServiceHealthCheckUrl = MysiteRestTestContainer.contentUrl(healthCheckUrl)
    val mysiteAuthServiceHealthCheckUrl = MysiteAuthTestContainer.authUrl(healthCheckUrl)

    given("Loadbalancer calls health check endpoint /health for mysite microservices") {
        `when` ("mysite rest health check") {
            then("200 (OK) returned") {
                val (_, response, _) = mysiteRestServiceHealthCheckUrl.httpGet().response()

                response.statusCode shouldBe 200
            }
        }
        `when` ("mysite auth health check") {
            then("200 (OK) returned") {
                val (_, response, _) = mysiteAuthServiceHealthCheckUrl.httpGet().response()

                response.statusCode shouldBe 200
            }
        }
    }
})