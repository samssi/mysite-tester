package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isUrlValid
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
                isUrlValid(emptyString) shouldBe false
            }
        }
        `when`("url without protocol as url ($urlWithoutProtocol)") {
            then ("false returned") {
                isUrlValid(urlWithoutProtocol) shouldBe false
            }
        }
        `when`("url with http protocol as url ($urlWithHttpProtocol)") {
            then ("true returned") {
                isUrlValid(urlWithHttpProtocol) shouldBe true
            }
        }
        `when`("url with https protocol as url ($urlWithHttpsProtocol)") {
            then ("false returned") {
                isUrlValid(urlWithHttpsProtocol) shouldBe true
            }
        }
        `when`("url with protocol and dash as url ($urlWithProtocolAndDash)") {
            then ("false returned") {
                isUrlValid(urlWithProtocolAndDash) shouldBe true
            }
        }
    }
})
