package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isYearValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class YearValidationUtilSpec : BehaviorSpec({
    val emptyString = ""
    val invalidYearTooShort = "198"
    val invalidYearTooLong = "19822"
    val invalidYear18xx = "1882"
    val invalidYear21xx = "2182"
    val invalidYearLetterInput = "asdf"
    val validYear20xx = "2000"
    val validYear19xx = "1900"

    given("Year validation call") {
        `when`("empty string as year ($emptyString)") {
            then ("false returned") {
                isYearValid(emptyString) shouldBe false
            }
        }
        `when`("invalid year too short ($invalidYearTooShort)") {
            then ("false returned") {
                isYearValid(invalidYearTooShort) shouldBe false
            }
        }
        `when`("invalid year too long ($invalidYearTooLong)") {
            then ("false returned") {
                isYearValid(invalidYearTooLong) shouldBe false
            }
        }
        `when`("invalid year 18xx ($invalidYear18xx)") {
            then ("false returned") {
                isYearValid(invalidYear18xx) shouldBe false
            }
        }
        `when`("invalid year 21xx ($invalidYear21xx)") {
            then ("false returned") {
                isYearValid(invalidYear21xx) shouldBe false
            }
        }
        `when`("invalid year letter input ($invalidYearLetterInput)") {
            then ("false returned") {
                isYearValid(invalidYearLetterInput) shouldBe false
            }
        }
        `when`("valid year 19xx ($validYear19xx)") {
            then ("true returned") {
                isYearValid(validYear19xx) shouldBe true
            }
        }
        `when`("valid year 20xx ($validYear20xx)") {
            then ("true returned") {
                isYearValid(validYear20xx) shouldBe true
            }
        }
    }
})