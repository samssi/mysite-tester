package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isPhoneNumberValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class PhoneNumberValidationUtilSpec : BehaviorSpec({
    val emptyString = ""
    val invalidNumericPhoneNumber = "12345"
    val invalidTextContentAsPhoneNumber = "+12344dsad2"
    val validInternationalFormatPhoneNumber = "+358 50 123 4567"
    val validFinnishPhoneNumber = "050 123 4567"

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
})