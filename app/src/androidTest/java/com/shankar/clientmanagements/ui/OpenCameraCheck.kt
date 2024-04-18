package com.shankar.clientmanagements.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressMenuKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shankar.clientmanagements.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.exitProcess


@LargeTest
@RunWith(AndroidJUnit4::class)
class OpenCameraCheck {

    @get:Rule
    val testRule = ActivityScenarioRule(Dashboard::class.java)

    @Test
    fun CheckCameraUI() {
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.text_view_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_profile)).perform(click())
        onView(withId(R.id.Edit_img)).perform(click())

        onView(withText("Open Camera")).check(matches(isDisplayed()))
        onView(withText("Browse Gallery")).check(matches(isDisplayed()))



    }
}