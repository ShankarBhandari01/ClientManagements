package com.shankar.clientmanagements.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shankar.clientmanagements.R
import org.hamcrest.core.IsAnything.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class settingCheckUI {
    @get:Rule
    val testRule = ActivityScenarioRule(SettingActivity::class.java)


    @Test
    fun CheckSettingUI() {
        onData(anything())
            .inAdapterView(withId(R.id.SettlingListView))
            .atPosition(0).perform(click())
        Espresso.onView(withId(R.id.cardView11))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()
        onData(anything())
            .inAdapterView(withId(R.id.SettlingListView))
            .atPosition(1).perform(click())

        Espresso.onView(withId(R.id.cardView11))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        pressBack()

    }

}