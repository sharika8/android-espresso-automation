package com.qa.automation.tests.navigation
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before; import org.junit.Test; import org.junit.runner.RunWith
import com.qa.automation.testdata.TestData; import com.qa.automation.tests.BaseTest

@RunWith(AndroidJUnit4::class) @LargeTest
class NavigationTests : BaseTest() {
    @Before override fun setUp() {
        loginPage.login(TestData.Credentials.valid.username, TestData.Credentials.valid.password)
    }
    @Test fun home_screenLoadsAfterLogin() { homePage.assertHomeScreenLoaded() }
    @Test fun home_canNavigateToSettings() { homePage.tapSettings() }
    @Test fun home_backNavigationWorks() { homePage.tapSettings(); Espresso.pressBack(); homePage.assertHomeScreenLoaded() }
    @Test fun home_canLogOut() { homePage.logout().assertLoginScreenVisible() }
    @Test fun home_searchFunctionality() { homePage.searchFor(TestData.Search.VALID_QUERY) }
    @Test fun home_listIsVisible() { homePage.assertListVisible() }
}
