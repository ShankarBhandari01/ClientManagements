package com.shankar.clientmanagements.ui

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    InstrumentedTest::class,
    FragmentsUI::class,
    settingCheckUI::class,
    OpenCameraCheck::class
)
class ActivityTestSuit {

}