package com.samssi.mysite.authentication

val emptyUserLogin = Login("", "")
val usernameCorrectPasswordIncorrect = Login("foobar", "bazboz")
val usernameInCorrectPasswordCorrectToSomeOtherUserPassword =
    Login("someuser", "logal")
val usernameCorrectPasswordCorrectToSomeOtherUser = Login("baz", "logal")

val usernameCorrectPasswordCorrectForFoobar = Login("foobar", "logal")
val usernameCorrectPasswordCorrectForBaz = Login("baz", "boz")

// General invalid class
data class Invalid(val foo: String)

// Login classes
data class Login(val username: String, val password: String)
data class Token(val token: String)

// Password util classes
data class Password(val password: String)
data class PasswordAndSalt(val password: String, val salt: String)

// Content classes
data class Message(val message: String)

