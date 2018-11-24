package com.samssi.mysite.content

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.samssi.mysite.authentication.postAuth
import com.samssi.mysite.authentication.usernameCorrectPasswordCorrectForFoobar
import com.samssi.mysite.testcontainer.MysiteRestTestContainer
import com.samssi.mysite.util.JsonUtil.jsonResponseAsType
import com.samssi.mysite.util.ValidationUtil.isOrderValid
import com.samssi.mysite.util.ValidationUtil.isPhoneNumberValid
import com.samssi.mysite.util.ValidationUtil.isPictureFormatValid
import com.samssi.mysite.util.ValidationUtil.isUrlValid
import com.samssi.mysite.util.ValidationUtil.isYearValid
import com.samssi.mysite.util.ValidationUtil.isZipCodeValid
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class PrivateContentSpec: BehaviorSpec({
    val documents = listOf("application", "about", "personal", "experience", "portfolio", "about")

    given("User calls for any content api private route") {
        `when`("no token in header") {
            then("""Forbidden(403) and message "No token provided."""") {
                assertDocumentHttpCallForbidden(documents)
            }
        }
        // TODO: bad request from backend?
        `when`("invalid token in header") {
            then("""Forbidden(403) and message "No token provided."""") {
                assertDocumentHttpCallForbiddenWithInvalidToken(documents)
            }
        }
    }
    given("Authorized user with valid JWT token calls for content api") {
        `when` (contentApiDocumentDescriptionText("about")) {
            then("""About document is returned""") {
                val response = authorizedContentGet(documentUrl("about"))
                val about = jsonResponseAsType<About>(response)

                response.statusCode shouldBe 200

                about.header.length shouldBeGreaterThan 0
                about.description.length shouldBeGreaterThan 0
                about.technologies.forEach{technology ->
                    technology.length shouldBeGreaterThan 0
                }
                about.services.forEach{service ->
                    service.description.length shouldBeGreaterThan 0
                    service.title.length shouldBeGreaterThan 0
                    isUrlValid(service.github) shouldBe true
                }
            }
        }
        `when` (contentApiDocumentDescriptionText("application")) {
            then("""Application document is returned""") {
                val response = authorizedContentGet(documentUrl("application"))
                val application = jsonResponseAsType<Application>(response)

                response.statusCode shouldBe 200

                application.greeting.length shouldBeGreaterThan 0
                application.signature.length shouldBeGreaterThan 0
                application.signatureTitle.length shouldBeGreaterThan 0
                application.paragraphs.forEach{ paragraph ->
                    paragraph.paragraph.length shouldBeGreaterThan 0
                }
            }
        }
        `when` (contentApiDocumentDescriptionText("personalInfo")) {
            then("""PersonalInfo document is returned""") {
                val response = authorizedContentGet(documentUrl("personal"))
                val personalInfo = jsonResponseAsType<PersonalInfo>(response)

                response.statusCode shouldBe 200

                personalInfo.title.length shouldBeGreaterThan 0
                personalInfo.name.length shouldBeGreaterThan 0
                isPhoneNumberValid(personalInfo.phoneNumber) shouldBe true
                personalInfo.applicationDate.length shouldBeGreaterThan 0
                isPictureFormatValid(personalInfo.picture) shouldBe true
                personalInfo.address.city.length shouldBeGreaterThan 0
                personalInfo.address.street.length shouldBeGreaterThan 0
                isZipCodeValid(personalInfo.address.zipcode) shouldBe true
            }
        }

        `when` (contentApiDocumentDescriptionText("experience")) {
            then("""Experience document is returned""") {
                val response = authorizedContentGet(documentUrl("experience"))
                val experienceDocs = jsonResponseAsType<Array<Experience>>(response)

                response.statusCode shouldBe 200
                experienceDocs.forEach { experience ->
                    experience.header.length shouldBeGreaterThanOrEqual 0
                    isOrderValid(experience.order) shouldBe true
                    experience.blocks.forEach{block ->
                        block.content.length shouldBeGreaterThan 0
                        block.title.length shouldBeGreaterThanOrEqual 0
                    }
                }
            }
        }
        `when` (contentApiDocumentDescriptionText("portfolio")) {
            then("""Portfolio document is returned""") {
                val response = authorizedContentGet(documentUrl("portfolio"))
                val portfolioDocs = jsonResponseAsType<Array<Portfolio>>(response)

                response.statusCode shouldBe 200

                portfolioDocs.forEach {portfolio ->
                    isOrderValid(portfolio.order) shouldBe true
                    portfolio.company.length shouldBeGreaterThanOrEqual 0
                    checkAssignments(portfolio.assignments)
                }


            }
        }
    }
})


internal fun checkAssignments(assignments: List<Assignments>) {
    assignments.forEach {assignment ->
        isYearValid(assignment.year.orEmpty()) shouldBe true
        assignment.technologies.forEach {technology ->
            technology.length shouldBeGreaterThan 0
        }
        assignment.paragraphs.forEach {paragraph ->
            paragraph.paragraph.length shouldBeGreaterThan 0
        }
    }
}

internal fun authorizedContentGet(url: String): Response {
    val (_, response, _) = url
        .httpGet()
        .header("Authorization" to validTokenFromAuthService())
        .response()
    return response
}

internal fun contentApiDocumentDescriptionText(document: String): String {
    return "content api responds to the user request for document: $document"
}

internal fun documentUrl(document: String): String {
    val contentUrl = MysiteRestTestContainer.contentUrl("/api/v1/content/private/contents")
    return "$contentUrl/$document"
}

internal fun validTokenFromAuthService(): String {
    val (_, token) = postAuth(usernameCorrectPasswordCorrectForFoobar)
    return token
}

internal fun assertDocumentHttpCallForbidden(documents: List<String>) {
    documents.forEach {document ->
        val response = unauthorizedContentGet(document)
        val message = jsonResponseAsType<Message>(response)

        response.statusCode shouldBe 403
        message.message shouldBe "No token provided."
    }
}

internal fun assertDocumentHttpCallForbiddenWithInvalidToken(documents: List<String>) {
    documents.forEach {document ->
        val response = invalidTokenContentGet(document)
        val message = jsonResponseAsType<Message>(response)

        response.statusCode shouldBe 403
        // TODO: fix to backend authenticate --> authorize
        message.message shouldBe "Failed to authenticate token."
    }
}

internal fun invalidTokenContentGet(document: String): Response {
    // TODO: signature is verified not the jwt content. Create own jwt and sign it with invalid secret
    val (_, response, _) = documentUrl(document)
        .httpGet()
        .header("Authorization" to "INVALID_TOKEN")
        .response()
    return response
}

internal fun unauthorizedContentGet(document: String): Response {
    val (_, response, _) = documentUrl(document).httpGet().response()
    return response
}