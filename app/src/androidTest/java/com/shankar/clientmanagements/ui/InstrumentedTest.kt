package com.shankar.clientmanagements.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shankar.clientmanagements.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLoginUI() {
        onView(withId(R.id.UserName))
            .perform(typeText("haribhandari"))
        onView(withId(R.id.Password))
            .perform(typeText("shankar01"))
        closeSoftKeyboard()
        onView(withId(R.id.chkRememberme)).perform(click())
        onView(withId(R.id.btn_Login)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.container)).check(matches(isDisplayed()))
    }

    @Test
    fun CheckViewUI() {
        onView(withId(R.id.btn_Login)).check(matches(isDisplayed()))
        onView(withId(R.id.UserName)).check(matches(isDisplayed()))
        onView(withId(R.id.linearLayout11)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.cardView11)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.Password)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        Thread.sleep(2000)
    }





}