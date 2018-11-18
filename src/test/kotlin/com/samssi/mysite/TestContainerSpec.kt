package com.samssi.mysite

import org.testcontainers.containers.GenericContainer

internal class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

internal object TestContainerSpec {
    private const val mysiteAuthContainerUri = "mysite/mysite-auth"
    internal val mysiteAuthContainer: GenericContainer<KGenericContainer>

    init {
        mysiteAuthContainer = GenericContainer(mysiteAuthContainerUri)
        mysiteAuthContainer.withExposedPorts(8100)
        mysiteAuthContainer.start()
    }

    fun authUrl(path: String): String {
        val authServiceUrl = AuthSettings.url
        val authServicePort = mysiteAuthContainer.getMappedPort(8100)
        return "${authServiceUrl}:${authServicePort}${path}"
    }
}