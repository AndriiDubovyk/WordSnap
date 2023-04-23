package com.andriidubovyk.wordsnap.presentation

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FlashcardsEntToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun saveNewNote_editAfterwards() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Click on add note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_flashcard))
            .performClick()

        // Enter word and definition and save note
        composeRule.onNodeWithTag(TestTags.WORD_TEXT_FIELD).performTextInput("Hello")
        composeRule.onNodeWithTag(TestTags.DEFINITION_TEXT_FIELD).performTextInput("Greetings")
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_flashcard))
            .performClick()

        // Click on created flashcard
        with (composeRule.onNodeWithText("Hello", substring = true)) {
            assertIsDisplayed()
            performClick()
        }

        // Check if opened flashcard had content that we entered
        composeRule
            .onNodeWithTag(TestTags.WORD_TEXT_FIELD)
            .assertTextEquals("Hello")
        composeRule
            .onNodeWithTag(TestTags.DEFINITION_TEXT_FIELD)
            .assertTextEquals("Greetings")

        // Add some text to word field and save flashcard
        composeRule
            .onNodeWithTag(TestTags.WORD_TEXT_FIELD)
            .performTextInput("Hello2")
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_flashcard))
            .performClick()

        composeRule.onNodeWithText("Hello2", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("Greetings", substring = true).assertIsDisplayed()
    }

    @Test
    fun saveNotValidNote_cantBeDone() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Click on add note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_flashcard))
            .performClick()

        // Save note without data - has same screen
        with(composeRule.onNodeWithContentDescription(context.getString(R.string.save_flashcard))) {
            performClick()
            assertIsDisplayed()
        }

        // Save note with only word - has same screen
        composeRule
            .onNodeWithTag(TestTags.WORD_TEXT_FIELD)
            .performTextInput("Word")
        with(composeRule.onNodeWithContentDescription(context.getString(R.string.save_flashcard))) {
            performClick()
            assertIsDisplayed()
        }

        // Save note with with word and translation - saved and go to flashcard screen
        composeRule
            .onNodeWithTag(TestTags.DEFINITION_TEXT_FIELD)
            .performTextInput("Definition")
        with(composeRule.onNodeWithContentDescription(context.getString(R.string.save_flashcard))) {
            performClick()
            assertDoesNotExist()
        }
    }

}