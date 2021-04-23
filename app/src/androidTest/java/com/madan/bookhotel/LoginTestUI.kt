package com.madan.bookhotel

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginTestUI {
    @get:Rule
    val testrule = ActivityScenarioRule(LoginActivity::class.java)
    @Test
    fun loginTestUI() {
        Espresso.onView(withId(R.id.etUsername))
                .perform(ViewActions.typeText("madan"))

        Espresso.onView(withId(R.id.etpassword))
                .perform(ViewActions.typeText("madan123"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.tvSignIn))
                .perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withId(R.id.bottom_navigation))
                .check(matches(isDisplayed()))
    }
}