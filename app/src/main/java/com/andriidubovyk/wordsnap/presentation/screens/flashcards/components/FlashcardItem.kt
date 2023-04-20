package com.andriidubovyk.wordsnap.presentation.screens.flashcards.components

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
import androidx.compose.ui.res.stringResource
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.domain.model.Flashcard

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
        Text("${stringResource(R.string.word)}: ${flashcard.word}")
        flashcard.definition?.let {
            Text("${stringResource(R.string.definition)}: $it")
        }
        flashcard.translation?.let {
            Text("${stringResource(R.string.translation)}: $it")
        }
        Text("${stringResource(R.string.score)}: ${flashcard.score}")

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