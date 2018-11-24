package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isZipCodeValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class ZipCodeValidationSpec : BehaviorSpec({
    val emptyString = ""
    val tooShortZipCode = "391"
    val tooLongZipCode = "391000"
    val invalidCharacterInZipCode = "3910H"
    val validZipCode = "00020"

    given("ZipCode validation call") {
        `when`("empty string as zipcode ($emptyString)") {
            then("false returned") {
                isZipCodeValid(emptyString) shouldBe false
            }
        }
        `when`("too short value as zipcode ($tooShortZipCode)") {
            then("false returned") {
                isZipCodeValid(tooShortZipCode) shouldBe false
            }
        }
        `when`("too long value as zipcode ($tooLongZipCode)") {
            then("false returned") {
                isZipCodeValid(tooLongZipCode) shouldBe false
            }
        }
        `when`("invalid character in zipcode ($invalidCharacterInZipCode)") {
            then("false returned") {
                isZipCodeValid(invalidCharacterInZipCode) shouldBe false
            }
        }
        `when`("valid zipcode ($validZipCode)") {
            then("true returned") {
                isZipCodeValid(validZipCode) shouldBe true
            }
        }
    }
})