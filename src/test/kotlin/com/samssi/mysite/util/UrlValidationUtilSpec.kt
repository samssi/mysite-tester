package com.samssi.mysite.util

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class UrlValidationUtilSpec : BehaviorSpec({
    val emptyString = ""
    val urlWithoutProtocol = "example.com"
    val urlWithHttpProtocol = "http://example.com"
    val urlWithHttpsProtocol = "https://www.example.com"
    val urlWithProtocolAndDash = "https://www.example-foo.com"

    given("Url validation call") {
        `when`("empty string as url ($emptyString)") {
            then ("false returned") {
                ValidationUtil.isUrlValid(emptyString) shouldBe false
            }
        }
        `when`("url without protocol as url ($urlWithoutProtocol)") {
            then ("false returned") {
                ValidationUtil.isUrlValid(urlWithoutProtocol) shouldBe false
            }
        }
        `when`("url with http protocol as url ($urlWithHttpProtocol)") {
            then ("true returned") {
                ValidationUtil.isUrlValid(urlWithHttpProtocol) shouldBe true
            }
        }
        `when`("url with https protocol as url ($urlWithHttpsProtocol)") {
            then ("false returned") {
                ValidationUtil.isUrlValid(urlWithHttpsProtocol) shouldBe true
            }
        }
        `when`("url with protocol and dash as url ($urlWithProtocolAndDash)") {
            then ("false returned") {
                ValidationUtil.isUrlValid(urlWithProtocolAndDash) shouldBe true
            }
        }
    }
})
