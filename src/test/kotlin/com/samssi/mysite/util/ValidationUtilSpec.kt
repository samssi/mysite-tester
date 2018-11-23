package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isPhoneNumberValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class ValidationUtilSpec : BehaviorSpec({
    val emptyStringAsPhoneNumber = ""
    val invalidNumericPhoneNumber = "12345"
    val invalidTextContentAsPhoneNumber = "+12344dsad2"
    val validInternationalFormatPhoneNumber = "+358501234567"
    val validFinnishPhoneNumber = "0501234567"

    given("Phone number validation call") {
        `when`("empty string as phone number ($emptyStringAsPhoneNumber)") {
            then ("false returned") {
                isPhoneNumberValid(emptyStringAsPhoneNumber) shouldBe false
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
        `when`("valid finnish format phone number ($validInternationalFormatPhoneNumber)") {
            then ("true returned") {
                isPhoneNumberValid(validFinnishPhoneNumber) shouldBe true
            }
        }
    }
})