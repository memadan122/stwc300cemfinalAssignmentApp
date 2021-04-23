package com.madan.bookhotel

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.madan.bookhotel.adapter.DestinationAdpater
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class DestinationFragmentTestUI {
    @get:Rule
    val testrule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun bookTour() {
        runBlocking {
            var userRepos = UserRepository()
            ServiceBuilder.token = "Bearer " + userRepos.checkUser("madan", "madan123").token
            ServiceBuilder.user = userRepos.checkUser("madan", "madan123").data
            println(ServiceBuilder.token)
        }
        Espresso.onView(ViewMatchers.withId(R.id.ic_destination)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.RvRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DestinationAdpater.DestinationViewHolder>
                (1, clickOnViewChild(R.id.tvBook)),
        )


        Espresso.onView(ViewMatchers.withId(R.id.bottom_navigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    private fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) =
            ViewActions.click().perform(uiController, view.findViewById<View>(viewId))
    }

}