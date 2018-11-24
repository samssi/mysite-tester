package com.samssi.mysite.util

import com.samssi.mysite.util.ValidationUtil.isPictureFormatValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class PictureFormatValidationSpec : BehaviorSpec({
    val emptyString = ""
    val noExtension = "foo"
    val jpgExtension = "foo.jpg"
    val dotInFilenameAndTxtExtension = "foo.png.txt"
    val dotInFilenameAndPngExtension = "foo.txt.png"
    val pngExtension = "foo.png"

    given("Picture format validation call") {
        `when`("empty string as picture ($emptyString)") {
            then("false returned") {
                isPictureFormatValid(emptyString) shouldBe false
            }
        }
        `when`("no extension for picture ($noExtension)") {
            then("false returned") {
                isPictureFormatValid(noExtension) shouldBe false
            }
        }
        `when`("jpg extension for picture ($jpgExtension)") {
            then("false returned") {
                isPictureFormatValid(jpgExtension) shouldBe false
            }
        }
        `when`("dot in filename and extension for txt file ($dotInFilenameAndTxtExtension)") {
            then("false returned") {
                isPictureFormatValid(dotInFilenameAndTxtExtension) shouldBe false
            }
        }
        `when`("dot in filename and extension for png picture ($dotInFilenameAndPngExtension)") {
            then("true returned") {
                isPictureFormatValid(dotInFilenameAndPngExtension) shouldBe true
            }
        }
        `when`("png extension for picture ($pngExtension)") {
            then("true returned") {
                isPictureFormatValid(pngExtension) shouldBe true
            }
        }
    }
})