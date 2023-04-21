package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.components.PracticeButton
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model.PracticeFlashcardAction
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model.PracticeFlashcardEvent
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model.PracticeFlashcardViewModel
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model.UserAnswerRating
import com.andriidubovyk.wordsnap.presentation.navigation.Screen
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.components.FlashcardDisplay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PracticeFlashcardScreen(
    navController: NavController,
    viewModel: PracticeFlashcardViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.actionFlow.collectLatest { action ->
            when(action) {
                is PracticeFlashcardAction.GoToNextFlashcardPractice -> {
                    navController.navigate(Screen.PracticeFlashcard.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FlashcardDisplay(
            modifier = Modifier.weight(1f),
            flashcard = state.flashcard,
            isAnswerVisible = state.isAnswerVisible
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            if (state.isAnswerVisible && state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                   modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.BAD)
                        )
                    },
                   text = stringResource(R.string.bad),
                   color = Color.Red
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.OK)
                        )
                    },
                    text = stringResource(R.string.ok),
                    color = Color.LightGray
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.GOOD)
                        )
                              },
                    text = stringResource(R.string.good),
                    color = Color.Green
                )
            } else if (state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.ShowAnswer) },
                    text = stringResource(R.string.show_answer),
                    color = Color.LightGray
                )
            } else {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.GoToNext) },
                    text = stringResource(R.string.next),
                    color = Color.LightGray
                )
            }
        }
    }

}
