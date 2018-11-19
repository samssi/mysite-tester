package com.samssi.mysite.testcontainer

import com.samssi.mysite.KGenericContainer
import com.samssi.mysite.Settings
import org.testcontainers.containers.GenericContainer

internal object MysiteRestTestContainer {
    private const val mysiteRestContainerUri = "mysite/mysite-rest"
    private const val mysiteRestContainerPort = 8090
    internal val mysiteRestContainer: GenericContainer<KGenericContainer>

    init {
        mysiteRestContainer = GenericContainer(mysiteRestContainerUri)
        mysiteRestContainer.withExposedPorts(mysiteRestContainerPort)
        mysiteRestContainer.start()
    }

    fun contentUrl(path: String): String {
        val restServiceUrl = Settings.hostUrl
        val restServicePort = mysiteRestContainer.getMappedPort(mysiteRestContainerPort)
        return "${restServiceUrl}:${restServicePort}${path}"
    }
}