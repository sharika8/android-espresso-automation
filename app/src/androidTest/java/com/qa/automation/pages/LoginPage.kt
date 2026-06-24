package com.qa.automation.pages
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import com.qa.automation.R

class LoginPage : BasePage() {
    companion object {
        const val USERNAME_FIELD  = R.id.login_username_input
        const val PASSWORD_FIELD  = R.id.login_password_input
        const val LOGIN_BUTTON    = R.id.login_button
        const val ERROR_MESSAGE   = R.id.login_error_text
        const val FORGOT_PASSWORD = R.id.login_forgot_password_btn
        const val REMEMBER_ME     = R.id.login_remember_me_checkbox
    }

    fun enterUsername(u: String): LoginPage { typeInto(USERNAME_FIELD, u); return this }
    fun enterPassword(p: String): LoginPage { typeInto(PASSWORD_FIELD, p); return this }
    fun tapLoginButton(): LoginPage { clickOn(LOGIN_BUTTON); return this }
    fun tapForgotPassword(): LoginPage { clickOn(FORGOT_PASSWORD); return this }
    fun toggleRememberMe(): LoginPage { clickOn(REMEMBER_ME); return this }
    fun clearUsername(): LoginPage { onId(USERNAME_FIELD).perform(clearText()); return this }
    fun clearPassword(): LoginPage { onId(PASSWORD_FIELD).perform(clearText()); return this }

    fun login(username: String, password: String): HomePage {
        enterUsername(username); enterPassword(password); tapLoginButton()
        return HomePage()
    }
    fun attemptLogin(username: String, password: String): LoginPage {
        enterUsername(username); enterPassword(password); tapLoginButton()
        return this
    }

    fun isLoginButtonEnabled(): Boolean { var e = false; onId(LOGIN_BUTTON).check { v, _ -> e = v.isEnabled }; return e }
    fun isErrorVisible(): Boolean = isDisplayed(ERROR_MESSAGE)

    fun assertLoginScreenVisible() { assertDisplayed(USERNAME_FIELD); assertDisplayed(PASSWORD_FIELD); assertDisplayed(LOGIN_BUTTON) }
    fun assertErrorDisplayed(expectedMessage: String? = null) {
        assertDisplayed(ERROR_MESSAGE)
        if (expectedMessage != null) assertTextContains(ERROR_MESSAGE, expectedMessage)
    }
    fun assertNoError() = assertNotDisplayed(ERROR_MESSAGE)
    fun assertLoginButtonEnabled() = assertEnabled(LOGIN_BUTTON)
    fun assertLoginButtonDisabled() = assertDisabled(LOGIN_BUTTON)
    fun assertUsernameHint(hint: String) = assertHint(USERNAME_FIELD, hint)
    fun assertRememberMeUnchecked() = assertNotChecked(REMEMBER_ME)
    fun assertRememberMeChecked() = assertChecked(REMEMBER_ME)

    override fun verifyScreenLoaded() = isDisplayed(USERNAME_FIELD) && isDisplayed(LOGIN_BUTTON)
}
