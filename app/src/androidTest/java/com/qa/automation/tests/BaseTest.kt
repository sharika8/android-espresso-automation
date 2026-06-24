package com.qa.automation.tests
import org.junit.After; import org.junit.Before; import org.junit.Rule
import org.junit.rules.TestName; import org.junit.rules.TestWatcher; import org.junit.runner.Description
import com.qa.automation.utils.ScreenshotUtils
import com.qa.automation.pages.LoginPage; import com.qa.automation.pages.HomePage

abstract class BaseTest {
    @get:Rule val testName = TestName()
    @get:Rule val screenshotRule = object : TestWatcher() {
        override fun failed(e: Throwable?, description: Description?) {
            ScreenshotUtils.captureOnFailure(description?.methodName ?: "unknown")
        }
    }
    val loginPage: LoginPage get() = LoginPage()
    val homePage: HomePage   get() = HomePage()
    @Before open fun setUp() {}
    @After  open fun tearDown() {}
}
