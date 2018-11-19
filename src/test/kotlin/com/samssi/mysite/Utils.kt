package com.samssi.mysite

import com.github.kittinunf.fuel.core.Response
import com.google.gson.Gson
import org.testcontainers.containers.GenericContainer

internal object JsonUtil {
    inline fun <reified T> jsonResponseAsType(response: Response) : T {
        return Gson().fromJson(String(response.data), T::class.java)
    }
}

internal class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)