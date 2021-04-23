package com.madan.bookhotel

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class SignUpTestUI {
    @get:Rule
    val testrule = ActivityScenarioRule(SignUpActivity::class.java)
    @Test
    fun signUpTestUI() {
        Espresso.onView(ViewMatchers.withId(R.id.etFirstname))
            .perform(ViewActions.typeText("madan"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etLastname))
            .perform(ViewActions.typeText("bastakoti"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etAddress))
            .perform(ViewActions.typeText("gorkha"))
        Espresso.closeSoftKeyboard()
        
        Espresso.onView(ViewMatchers.withId(R.id.etEmailAddress))
            .perform(ViewActions.typeText("madan@gmail.com"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etPhoneNumber))
            .perform(ViewActions.typeText("9865005111"))
        Espresso.closeSoftKeyboard()


        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText("madan"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("madan123"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etConfirmPassword))
            .perform(ViewActions.typeText("madan123"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.tvSignUp))
            .perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.tvSignIn))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}