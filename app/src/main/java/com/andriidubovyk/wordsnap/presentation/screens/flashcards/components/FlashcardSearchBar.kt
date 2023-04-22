package com.andriidubovyk.wordsnap.presentation.screens.flashcards.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.components.AdvancedTextField

@Composable
fun FlashcardSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onReset: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AdvancedTextField(
            modifier = Modifier.weight(1f),
            value = value,
            lines = 1,
            onValueChange = onValueChange,
            placeholderText = stringResource(R.string.search)
        )
        IconButton(
            onClick = { onReset() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.reset_search)
            )
        }
    }
}