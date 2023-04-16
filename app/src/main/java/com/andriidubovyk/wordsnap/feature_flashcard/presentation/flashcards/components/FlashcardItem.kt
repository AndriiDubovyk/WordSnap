package com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard

@Composable
fun FlashcardItem(
    flashcard: Flashcard,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Text("Word: ${flashcard.word}")
        flashcard.definition?.let {
            Text("Definition: $it")
        }
        flashcard.translation?.let {
            Text("Translation: $it")
        }
        Text("Score: ${flashcard.score}")

        IconButton(
            onClick = onDeleteClick,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete flashcard",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}