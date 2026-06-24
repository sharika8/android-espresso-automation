# 🤖 Android Espresso Automation Framework

Native Android UI automation in **Kotlin + Espresso + JUnit4** — Page Object Model, parameterised data-driven tests, screenshot-on-failure, UiAutomator integration, and GitHub Actions CI with real emulators (API 31 + 33 matrix).

[![CI](https://github.com/sharika8/android-espresso-automation/actions/workflows/espresso.yml/badge.svg)](https://github.com/sharika8/android-espresso-automation/actions/workflows/espresso.yml)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-purple?logo=kotlin)](https://kotlinlang.org)
[![Espresso](https://img.shields.io/badge/Espresso-3.6-green?logo=android)](https://developer.android.com/training/testing/espresso)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen)](https://developer.android.com/about/versions/oreo)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## ✨ Features

| Feature | Detail |
|---|---|
| **Page Object Model** | `BasePage` with Espresso helpers: `clickOn`, `typeInto`, `assertDisplayed` |
| **Page classes** | `LoginPage`, `HomePage` — fluent, chainable API |
| **Parameterised tests** | JUnit4 `@Parameterized` — data-driven invalid credential testing |
| **Screenshot on failure** | `TestWatcher` rule + `UiDevice.takeScreenshot()` |
| **UiAutomator** | System-level waits, permission dialogs, device interactions |
| **Test Orchestrator** | Each test in isolated process — no shared state |
| **Animations disabled** | `testOptions { animationsDisabled true }` for reliable tests |
| **CI** | `reactivecircus/android-emulator-runner`, API 31 + 33 matrix |

---

## 📁 Project Structure

```
android-espresso-automation/
├── app/
│   ├── build.gradle                  # Espresso, UiAutomator, Orchestrator deps
│   └── src/androidTest/java/com/qa/automation/
│       ├── pages/
│       │   ├── BasePage.kt           # click, type, assertDisplayed, assertTextContains
│       │   ├── LoginPage.kt          # enterUsername, login(), attemptLogin(), assertErrorDisplayed()
│       │   └── HomePage.kt           # tapSettings, logout(), searchFor(), assertHomeScreenLoaded()
│       ├── tests/
│       │   ├── BaseTest.kt           # Screenshot-on-failure rule, page object accessors
│       │   ├── login/LoginTests.kt   # Smoke, negative, parameterised, accessibility (4 classes)
│       │   └── navigation/NavigationTests.kt
│       ├── utils/
│       │   ├── WaitUtils.kt          # Espresso + UiAutomator waits + idling resource helpers
│       │   └── ScreenshotUtils.kt    # UiDevice screenshot capture to external storage
│       └── testdata/TestData.kt      # Credentials, messages, SQL injection + XSS payloads
└── .github/workflows/espresso.yml   # CI: syntax check + emulator matrix (API 31, 33)
```

---

## 🚀 Quick Start

### Prerequisites
- Android Studio Hedgehog (2023.1.1)+ or Ladybug (2024.2.1)+
- Android SDK API 26+, Java 17

```bash
# Run all instrumented tests
./gradlew connectedDebugAndroidTest

# Run specific class
./gradlew connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=\
  com.qa.automation.tests.login.LoginSmokeTests

# Run specific test method
./gradlew connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=\
  com.qa.automation.tests.login.LoginSmokeTests#login_successWithValidCredentials
```

---

## 🏗️ Writing Tests

```kotlin
@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginSmokeTests : BaseTest() {

    @Test
    fun login_successWithValidCredentials() {
        val home = loginPage.login(
            TestData.Credentials.valid.username,
            TestData.Credentials.valid.password
        )
        home.assertHomeScreenLoaded()
    }

    @Test
    fun login_wrongPassword_showsError() {
        loginPage
            .attemptLogin("user@test.com", "wrongpass")
            .assertErrorDisplayed(TestData.Messages.INVALID_CREDENTIALS)
    }
}

// Parameterised — runs once per invalid credential set
@RunWith(Parameterized::class)
class LoginParameterisedTests(private val username: String, ...) : BaseTest() {
    @Test
    fun login_invalidCredentials_showsErrorOrStaysOnPage() { ... }
}
```

---

## 📊 Test Coverage

| Class | Tests | Focus |
|---|---|---|
| `LoginSmokeTests` | 3 | Happy path, logout |
| `LoginNegativeTests` | 7 | Wrong creds, empty, injection, XSS |
| `LoginParameterisedTests` | 7 (data-driven) | All invalid credential combos |
| `LoginAccessibilityTests` | 3 | Remember-me, button state |
| `NavigationTests` | 6 | Screen navigation, back, logout |
| **Total** | **26** | |

---

## 🤖 CI Pipeline

| Job | Trigger | Details |
|---|---|---|
| `syntax-check` | every push | Validates Kotlin files, counts @Test annotations, checks build.gradle |
| `espresso-tests` | every push | API 31 + 33 emulator matrix via `android-emulator-runner` |
| `all-passed` | always | Summary gate |

---

## 🔗 Related Repos

| Repo | Description |
|---|---|
| [ios-xcuitest-automation](https://github.com/sharika8/ios-xcuitest-automation) | Native iOS XCUITest (Swift) |
| [mobile-appium-framework](https://github.com/sharika8/mobile-appium-framework) | Cross-platform Appium (Python) |
| [playwright-typescript-framework](https://github.com/sharika8/playwright-typescript-framework) | Web UI + API (TypeScript) |

---

## 📜 Licence
MIT