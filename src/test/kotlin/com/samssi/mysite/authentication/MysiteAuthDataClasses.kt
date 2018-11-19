package com.samssi.mysite.authentication

val emptyUserLogin = Login("", "")
val usernameCorrectPasswordIncorrect = Login("foobar", "bazboz")
val usernameInCorrectPasswordCorrectToSomeOtherUserPassword =
    Login("someuser", "logal")
val usernameCorrectPasswordCorrectToSomeOtherUser = Login("baz", "logal")

val usernameCorrectPasswordCorrectForFoobar = Login("foobar", "logal")
val usernameCorrectPasswordCorrectForBaz = Login("baz", "boz")

data class Login(val username: String, val password: String)
data class Token(val token: String)

data class InvalidPasswordClass(val passwd: String)
data class Password(val password: String)
data class PasswordAndSalt(val password: String, val salt: String)
