package com.qa.automation.pages
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import com.qa.automation.utils.WaitUtils
import com.qa.automation.utils.ScreenshotUtils

abstract class BasePage {
    fun onId(resId: Int) = onView(withId(resId))
    fun onText(text: String) = onView(withText(text))
    fun onContentDesc(desc: String) = onView(withContentDescription(desc))

    fun clickOn(resId: Int) { WaitUtils.waitForView(withId(resId)); onId(resId).perform(click()) }
    fun clickOn(text: String) { WaitUtils.waitForView(withText(text)); onText(text).perform(click()) }

    fun typeInto(resId: Int, text: String, clearFirst: Boolean = true) {
        WaitUtils.waitForView(withId(resId))
        if (clearFirst) onId(resId).perform(clearText())
        onId(resId).perform(typeText(text), closeSoftKeyboard())
    }

    fun assertDisplayed(resId: Int) { WaitUtils.waitForView(withId(resId)); onId(resId).check(matches(isDisplayed())) }
    fun assertDisplayed(text: String) { WaitUtils.waitForView(withText(text)); onText(text).check(matches(isDisplayed())) }
    fun assertNotDisplayed(resId: Int) { onId(resId).check(matches(not(isDisplayed()))) }
    fun assertEnabled(resId: Int) { onId(resId).check(matches(isEnabled())) }
    fun assertDisabled(resId: Int) { onId(resId).check(matches(not(isEnabled()))) }
    fun assertTextEquals(resId: Int, expected: String) { WaitUtils.waitForView(withId(resId)); onId(resId).check(matches(withText(expected))) }
    fun assertTextContains(resId: Int, substring: String) { WaitUtils.waitForView(withId(resId)); onId(resId).check(matches(withText(containsString(substring)))) }
    fun assertHint(resId: Int, hint: String) { onId(resId).check(matches(withHint(hint))) }
    fun assertChecked(resId: Int) { onId(resId).check(matches(isChecked())) }
    fun assertNotChecked(resId: Int) { onId(resId).check(matches(isNotChecked())) }
    fun assertContentDescription(resId: Int, desc: String) { onId(resId).check(matches(withContentDescription(desc))) }

    fun isDisplayed(resId: Int): Boolean = try { onId(resId).check(matches(isDisplayed())); true } catch (e: Exception) { false }
    fun exists(resId: Int): Boolean = try { onId(resId).check(matches(any(View::class.java))); true } catch (e: Exception) { false }

    fun takeScreenshot(name: String) { ScreenshotUtils.capture(name) }
    abstract fun verifyScreenLoaded(): Boolean
}
