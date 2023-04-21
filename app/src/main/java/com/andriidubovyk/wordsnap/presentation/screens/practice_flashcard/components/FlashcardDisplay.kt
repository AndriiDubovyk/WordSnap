package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.components

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.presentation.components.AdvancedTextField
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.components.DescriptionText

@Composable
fun FlashcardDisplay(
    modifier: Modifier = Modifier,
    flashcard: Flashcard?,
    isAnswerVisible: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText(
            descName = stringResource(R.string.word),
            descValue = flashcard?.word?: stringResource(R.string.no_words_to_study),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (isAnswerVisible) {
            flashcard?.definition?.let {
                DescriptionText(
                    descName = stringResource(R.string.definition),
                    descValue = it,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            flashcard?.translation?.let {
                DescriptionText(
                    descName = stringResource(R.string.translation),
                    descValue = it,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            var text by remember { mutableStateOf("") }
            AdvancedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                value = text,
                onValueChange = { text = it },
                lines = 1,
                placeholderText = "You can type your answer if you want",
            )
        }
    }
}