package com.qa.automation.pages
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.qa.automation.R

class HomePage : BasePage() {
    companion object {
        const val TOOLBAR       = R.id.home_toolbar
        const val WELCOME_TEXT  = R.id.home_welcome_text
        const val RECYCLER_VIEW = R.id.home_recycler_view
        const val SEARCH_VIEW   = R.id.home_search_view
        const val LOGOUT_MENU   = R.id.menu_logout
        const val SETTINGS_MENU = R.id.menu_settings
        const val PROFILE_MENU  = R.id.menu_profile
    }

    fun openMenu() { onView(withContentDescription("More options")).perform(click()) }
    fun tapProfile(): HomePage { openMenu(); clickOn(PROFILE_MENU); return this }
    fun tapSettings(): HomePage { openMenu(); clickOn(SETTINGS_MENU); return this }
    fun logout(): LoginPage { openMenu(); clickOn(LOGOUT_MENU); return LoginPage() }
    fun searchFor(q: String): HomePage { clickOn(SEARCH_VIEW); onId(SEARCH_VIEW).perform(typeText(q)); return this }

    fun assertHomeScreenLoaded() = assertDisplayed(TOOLBAR)
    fun assertWelcomeMessageContains(text: String) = assertTextContains(WELCOME_TEXT, text)
    fun assertListVisible() = assertDisplayed(RECYCLER_VIEW)

    override fun verifyScreenLoaded() = isDisplayed(TOOLBAR) || isDisplayed(WELCOME_TEXT)
}
