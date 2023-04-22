package com.andriidubovyk.wordsnap.presentation.screens.flashcards.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model.FlashcardsViewModel

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    flashcardOrder: FlashcardOrder = FlashcardsViewModel.DEFAULT_ORDER,
    onOrderChange: (FlashcardOrder) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(targetState = flashcardOrder.orderType) {
            when(it) {
                OrderType.Ascending -> {
                    IconButton(
                        onClick = {
                            onOrderChange(flashcardOrder.copy(OrderType.Descending))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = stringResource(R.string.ascending)
                        )
                    }
                }
                OrderType.Descending -> {
                    IconButton(
                        onClick = {
                            onOrderChange(flashcardOrder.copy(OrderType.Ascending))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = stringResource(R.string.descending)
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DefaultRadioButton(
                    text = stringResource(R.string.word),
                    selected = flashcardOrder is FlashcardOrder.Word,
                    onSelect = { onOrderChange(FlashcardOrder.Word(flashcardOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(R.string.definition),
                    selected = flashcardOrder is FlashcardOrder.Definition,
                    onSelect = { onOrderChange(FlashcardOrder.Definition(flashcardOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(R.string.translation),
                    selected = flashcardOrder is FlashcardOrder.Translation,
                    onSelect = { onOrderChange(FlashcardOrder.Translation(flashcardOrder.orderType)) }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DefaultRadioButton(
                    text = stringResource(R.string.score),
                    selected = flashcardOrder is FlashcardOrder.Score,
                    onSelect = { onOrderChange(FlashcardOrder.Score(flashcardOrder.orderType)) }
                )
                DefaultRadioButton(
                    text = stringResource(R.string.date),
                    selected = flashcardOrder is FlashcardOrder.Date,
                    onSelect = { onOrderChange(FlashcardOrder.Date(flashcardOrder.orderType)) }
                )
            }
        }
    }
}