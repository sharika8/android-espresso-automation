package com.qa.automation.utils
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher

object WaitUtils {
    private const val DEFAULT_TIMEOUT_MS = 10_000L

    fun waitForView(matcher: Matcher<View>, timeoutMs: Long = DEFAULT_TIMEOUT_MS) {
        val deadline = System.currentTimeMillis() + timeoutMs
        var last: Exception? = null
        while (System.currentTimeMillis() < deadline) {
            try { onView(matcher).check(matches(isDisplayed())); return }
            catch (e: Exception) { last = e; Thread.sleep(250) }
        }
        throw last ?: AssertionError("View not found within ${timeoutMs}ms: $matcher")
    }

    fun waitForViewToDisappear(matcher: Matcher<View>, timeoutMs: Long = DEFAULT_TIMEOUT_MS) {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            try { onView(matcher).check(matches(isDisplayed())); Thread.sleep(250) }
            catch (e: Exception) { return }
        }
    }

    fun registerIdlingResource(r: IdlingResource) = IdlingRegistry.getInstance().register(r)
    fun unregisterIdlingResource(r: IdlingResource) = IdlingRegistry.getInstance().unregister(r)
    fun waitMillis(ms: Long) = Thread.sleep(ms)
}
