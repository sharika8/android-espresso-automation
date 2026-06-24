package com.qa.automation.utils
import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import java.io.File
import java.text.SimpleDateFormat; import java.util.Date; import java.util.Locale

object ScreenshotUtils {
    private val outputDir: File by lazy {
        val dir = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "TestScreenshots")
        dir.mkdirs(); dir
    }

    fun capture(name: String): File? = try {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val ts = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val file = File(outputDir, "${name}_$ts.png")
        if (device.takeScreenshot(file)) file else null
    } catch (e: Exception) { null }

    fun captureOnFailure(testName: String) = capture("FAIL_$testName")
}
