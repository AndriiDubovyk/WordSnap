package com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PracticeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}