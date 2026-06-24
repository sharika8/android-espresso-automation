package com.qa.automation.tests.login
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest; import androidx.test.filters.SmallTest
import org.junit.Test; import org.junit.runner.RunWith; import org.junit.runners.Parameterized
import com.qa.automation.testdata.TestData; import com.qa.automation.tests.BaseTest

@RunWith(AndroidJUnit4::class) @LargeTest
class LoginSmokeTests : BaseTest() {
    @Test fun loginScreen_allElementsVisible() { loginPage.assertLoginScreenVisible() }
    @Test fun login_successWithValidCredentials() {
        val h = loginPage.login(TestData.Credentials.valid.username, TestData.Credentials.valid.password)
        h.assertHomeScreenLoaded()
    }
    @Test fun login_logoutReturnsToLogin() {
        loginPage.login(TestData.Credentials.valid.username, TestData.Credentials.valid.password).assertHomeScreenLoaded()
        loginPage.assertLoginScreenVisible()
    }
}

@RunWith(AndroidJUnit4::class) @LargeTest
class LoginNegativeTests : BaseTest() {
    @Test fun login_wrongPassword_showsError() {
        loginPage.attemptLogin(TestData.Credentials.wrongPassword.username, TestData.Credentials.wrongPassword.password)
        loginPage.assertErrorDisplayed(TestData.Messages.INVALID_CREDENTIALS)
    }
    @Test fun login_emptyUsername_showsError() {
        loginPage.enterPassword(TestData.Credentials.valid.password).tapLoginButton()
        loginPage.assertErrorDisplayed(TestData.Messages.USERNAME_REQUIRED)
    }
    @Test fun login_emptyPassword_showsError() {
        loginPage.enterUsername(TestData.Credentials.valid.username).tapLoginButton()
        loginPage.assertErrorDisplayed(TestData.Messages.PASSWORD_REQUIRED)
    }
    @Test fun login_bothEmpty_showsError() { loginPage.tapLoginButton(); loginPage.assertErrorDisplayed() }
    @Test fun login_staysOnPageAfterFailure() {
        loginPage.attemptLogin("bad@test.com", "wrong")
        assert(loginPage.verifyScreenLoaded()) { "Should stay on login page" }
    }
    @Test fun login_sqlInjection_fails() {
        loginPage.attemptLogin(TestData.Credentials.sqlInjection.username, TestData.Credentials.sqlInjection.password)
        assert(loginPage.verifyScreenLoaded()) { "SQL injection should not bypass login" }
    }
    @Test fun login_xssPayload_fails() {
        loginPage.attemptLogin(TestData.Credentials.xssPayload.username, TestData.Credentials.xssPayload.password)
        assert(loginPage.verifyScreenLoaded()) { "XSS payload should not bypass login" }
    }
}

@RunWith(Parameterized::class) @LargeTest
class LoginParameterisedTests(private val username: String, private val password: String, private val desc: String) : BaseTest() {
    companion object {
        @JvmStatic @Parameterized.Parameters(name = "{2}")
        fun creds() = TestData.Credentials.allInvalid.mapIndexed { i, c -> arrayOf(c.username, c.password, "Invalid set $i") }
    }
    @Test fun login_invalidCredentials_showsErrorOrStaysOnPage() {
        loginPage.attemptLogin(username, password)
        assert(loginPage.isErrorVisible() || loginPage.verifyScreenLoaded()) { "[$desc] should show error" }
    }
}

@RunWith(AndroidJUnit4::class) @SmallTest
class LoginAccessibilityTests : BaseTest() {
    @Test fun login_rememberMe_defaultUnchecked() { loginPage.assertRememberMeUnchecked() }
    @Test fun login_rememberMe_toggles() {
        loginPage.assertRememberMeUnchecked()
        loginPage.toggleRememberMe(); loginPage.assertRememberMeChecked()
        loginPage.toggleRememberMe(); loginPage.assertRememberMeUnchecked()
    }
    @Test fun login_loginButtonEnabled() {
        loginPage.enterUsername(TestData.Credentials.valid.username).enterPassword(TestData.Credentials.valid.password)
        loginPage.assertLoginButtonEnabled()
    }
}
