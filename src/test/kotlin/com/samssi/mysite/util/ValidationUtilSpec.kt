package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isPhoneNumberValid
import com.samssi.mysite.util.ValidationUtil.isUrlValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class ValidationUtilSpec : BehaviorSpec({
    val emptyString = ""
    val invalidNumericPhoneNumber = "12345"
    val invalidTextContentAsPhoneNumber = "+12344dsad2"
    val validInternationalFormatPhoneNumber = "+358501234567"
    val validFinnishPhoneNumber = "0501234567"

    given("Phone number validation call") {
        `when`("empty string as phone number ($emptyString)") {
            then ("false returned") {
                isPhoneNumberValid(emptyString) shouldBe false
            }
        }
        `when`("invalid text content as phone number ($invalidTextContentAsPhoneNumber)") {
            then ("false returned") {
                isPhoneNumberValid(invalidNumericPhoneNumber) shouldBe false
            }
        }
        `when`("invalid phone number ($invalidNumericPhoneNumber)") {
            then ("false returned") {
                isPhoneNumberValid(invalidNumericPhoneNumber) shouldBe false
            }
        }
        `when`("valid international format phone number ($validInternationalFormatPhoneNumber)") {
            then ("true returned") {
                isPhoneNumberValid(validInternationalFormatPhoneNumber) shouldBe true
            }
        }
        `when`("valid finnish format phone number ($validFinnishPhoneNumber)") {
            then ("true returned") {
                isPhoneNumberValid(validFinnishPhoneNumber) shouldBe true
            }
        }
    }

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