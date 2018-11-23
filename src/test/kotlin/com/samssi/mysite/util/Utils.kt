package com.samssi.mysite.util

import com.github.kittinunf.fuel.core.Response
import com.google.gson.Gson
import org.testcontainers.containers.GenericContainer

internal object JsonUtil {
    inline fun <reified T> jsonResponseAsType(response: Response) : T {
        return Gson().fromJson(String(response.data), T::class.java)
    }
}

internal class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

internal object ValidationUtil {
    internal fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val regex = """(\+|0)[0-9 ]*""".toRegex()
        return regex.matches(phoneNumber)
    }

    internal fun isOrderValid(order: Int): Boolean { return order > -1 }

    internal fun isUrlValid(github: String): Boolean {
        val regex = "(http://|https://)[a-z0-9-./]*".toRegex()
        return regex.matches(github)
    }

    internal fun isYearValid(year: String): Boolean {
        val regex = "[1,2][0,9][0-9]{2}".toRegex()
        return regex.matches(year)
    }
}