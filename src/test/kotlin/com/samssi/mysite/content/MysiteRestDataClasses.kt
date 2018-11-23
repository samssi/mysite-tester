package com.samssi.mysite.content

data class About(val header: String, val description: String, val technologies: List<String>, val services: List<Services>)
data class Services(val title: String, val description: String, val github: String)

data class Application(val greeting: String, val signatureTitle: String)