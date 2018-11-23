package com.samssi.mysite.content

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.samssi.mysite.JsonUtil.jsonResponseAsType
import com.samssi.mysite.authentication.Message
import com.samssi.mysite.authentication.postAuth
import com.samssi.mysite.authentication.usernameCorrectPasswordCorrectForFoobar
import com.samssi.mysite.testcontainer.MysiteRestTestContainer
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import kotlin.reflect.full.memberProperties

internal class AboutSpec : BehaviorSpec({
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
                assertContentResponse(about)
            }
        }
        `when` (contentApiDocumentDescriptionText("application")) {
            then("""Application document is returned""") {
                val response = authorizedContentGet(documentUrl("application"))
                val application = jsonResponseAsType<Application>(response)

                response.statusCode shouldBe 200
                assertContentResponse(application)
            }
        }
    }
})

fun authorizedContentGet(url: String): Response {
    val (_, response, _) = url
        .httpGet()
        .header("Authorization" to validTokenFromAuthService())
        .response()
    return response
}

fun contentApiDocumentDescriptionText(document: String): String {
    return "content api responds to the user request for document: $document"
}

fun assertContentResponse(contentResource: Any) {
    contentResource::class.memberProperties.forEach{property ->
        val propertyValue = property.call(contentResource)
        if (propertyValue is String) {
            propertyValue.length shouldBeGreaterThan 0
        }

    }
}

fun documentUrl(document: String): String {
    val contentUrl = MysiteRestTestContainer.contentUrl("/api/v1/content/private/contents")
    return "$contentUrl/$document"
}

fun validTokenFromAuthService(): String {
    val (_, token) = postAuth(usernameCorrectPasswordCorrectForFoobar)
    return token
}
fun assertDocumentHttpCallForbidden(documents: List<String>) {
    documents.forEach {document ->
        val response = unauthorizedContentGet(document)
        val message = jsonResponseAsType<Message>(response)

        response.statusCode shouldBe 403
        message.message shouldBe "No token provided."
    }
}

fun assertDocumentHttpCallForbiddenWithInvalidToken(documents: List<String>) {
    documents.forEach {document ->
        val response = invalidTokenContentGet(document)
        val message = jsonResponseAsType<Message>(response)

        response.statusCode shouldBe 403
        // TODO: fix to backend authenticate --> authorize
        message.message shouldBe "Failed to authenticate token."
    }
}

fun invalidTokenContentGet(document: String): Response {
    // TODO: signature is verified not the jwt content. Create own jwt and sign it with invalid secret
    val (_, response, _) = documentUrl(document)
        .httpGet()
        .header("Authorization" to "INVALID_TOKEN")
        .response()
    return response
}

fun unauthorizedContentGet(document: String): Response {
    val (_, response, _) = documentUrl(document).httpGet().response()
    return response
}