package com.samssi.mysite.testcontainer

import com.samssi.mysite.Settings
import com.samssi.mysite.util.KGenericContainer
import org.testcontainers.containers.GenericContainer

internal object MysiteAuthTestContainer {
    private const val mysiteAuthContainerUri = "mysite/mysite-auth"
    private const val mysiteAuthContainerPort = 8100
    internal val mysiteAuthContainer: GenericContainer<KGenericContainer>

    init {
        mysiteAuthContainer = GenericContainer(mysiteAuthContainerUri)
        mysiteAuthContainer.withExposedPorts(mysiteAuthContainerPort)
        mysiteAuthContainer.start()
    }

    fun authUrl(path: String): String {
        val authServiceUrl = Settings.hostUrl
        val authServicePort = mysiteAuthContainer.getMappedPort(mysiteAuthContainerPort)
        return "${authServiceUrl}:${authServicePort}${path}"
    }
}