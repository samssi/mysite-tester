package com.samssi.mysite.content



data class About(val header: String, val description: String, val technologies: List<String>, val services: List<Services>)
data class Services(val title: String, val description: String, val github: String)

data class Application(val greeting: String, val signatureTitle: String, val signature: String, val paragraphs: List<Paragraphs>)
data class Paragraphs(val paragraph: String)

// Array of documents
data class Experience(val order: Int, val header: String, val blocks: List<Blocks>)
data class Blocks(val title: String, val content: String)

data class PersonalInfo(val title: String, val name: String, val address: Address, val phoneNumber: String,
                        val applicationDate: String, val picture: String)
data class Address(val street: String, val zipcode: String, val city: String)

data class Portfolio(val order: Int, val company: String, val assignments: List<Assignments>)
data class Assignments(val year: String, val paragraphs: List<Paragraphs>, val technologies: List<String>)