package com.qa.automation.testdata

object TestData {
    object Credentials {
        data class User(val username: String, val password: String)
        val valid        = User("testuser@example.com", "Test@1234!")
        val admin        = User("admin@example.com",    "Admin@9999!")
        val locked       = User("locked@example.com",   "Locked@123!")
        val wrongPassword  = User("testuser@example.com", "WrongPass999!")
        val wrongUsername  = User("nobody@example.com",   "Test@1234!")
        val emptyUsername  = User("",                     "Test@1234!")
        val emptyPassword  = User("testuser@example.com", "")
        val bothEmpty      = User("",                     "")
        val sqlInjection   = User("' OR '1'='1; --",     "password")
        val xssPayload     = User("<script>alert(1)</script>", "pass")
        val allInvalid = listOf(wrongPassword, wrongUsername, emptyUsername, emptyPassword, bothEmpty, sqlInjection, xssPayload)
    }
    object Messages {
        const val INVALID_CREDENTIALS = "Invalid username or password"
        const val USERNAME_REQUIRED   = "Username is required"
        const val PASSWORD_REQUIRED   = "Password is required"
        const val ACCOUNT_LOCKED      = "Account has been locked"
        const val WELCOME             = "Welcome"
    }
    object Search {
        const val VALID_QUERY = "Android"; const val NO_RESULTS = "zzznoresultsxxx"; const val EMPTY = ""
    }
}
