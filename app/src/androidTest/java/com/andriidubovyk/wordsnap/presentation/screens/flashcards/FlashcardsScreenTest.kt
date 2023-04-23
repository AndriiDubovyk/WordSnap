package com.andriidubovyk.wordsnap.presentation.screens.flashcards

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.andriidubovyk.wordsnap.MainActivity
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.common.TestTags
import com.andriidubovyk.wordsnap.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FlashcardsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickToggleOrderSection_isToggleable() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort)).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort)).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
    }

}