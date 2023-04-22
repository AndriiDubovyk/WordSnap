package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.presentation.components.AdvancedTextField
import com.andriidubovyk.wordsnap.presentation.components.DescriptionText
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model.PracticeFlashcardEvent
import androidx.compose.ui.unit.dp as dp

@Composable
fun FlashcardDisplay(
    modifier: Modifier = Modifier,
    flashcard: Flashcard?,
    isAnswerVisible: Boolean,
    onEvent: (PracticeFlashcardEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                ),
            onClick = { onEvent(PracticeFlashcardEvent.PronounceWord) }
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                imageVector = Icons.Default.Speaker,
                contentDescription = stringResource(R.string.pronounce_the_word),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = text,
                onValueChange = { text = it },
                lines = 1,
                placeholderText = stringResource(R.string.flahscard_practice_type_hint),
            )
        }
    }
}