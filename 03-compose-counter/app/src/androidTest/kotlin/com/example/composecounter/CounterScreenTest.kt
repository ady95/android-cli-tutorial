package com.example.composecounter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CounterScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun 시작하면_카운트는_0이다() {
        composeRule.onNodeWithText("Count: 0").assertIsDisplayed()
    }

    @Test
    fun 버튼을_두_번_누르면_카운트가_2가_된다() {
        composeRule.onNodeWithText("Count Up").performClick()
        composeRule.onNodeWithText("Count Up").performClick()
        composeRule.onNodeWithText("Count: 2").assertIsDisplayed()
    }
}
