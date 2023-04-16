package com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard.components.PracticeButton
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PracticeFlashcardScreen(
    navController: NavController,
    viewModel: PracticeFlashcardViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is PracticeFlashcardViewModel.UiEvent.GoToNextFlashcardPractice -> {
                    navController.navigate(Screen.PracticeFlashcard.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Word: ${state.flashcard?.word}")
            if (state.isAnswerVisible) {
                state.flashcard?.definition?.let {
                    Text("Definition: $it")
                }
                state.flashcard?.translation?.let {
                    Text("Translation: $it")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (state.isAnswerVisible && state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                   modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.BAD)
                        )
                    },
                   text = "BAD",
                   color = Color.Red
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.OK)
                        )
                    },
                    text = "OK",
                    color = Color.LightGray
                )
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.onEvent(
                            PracticeFlashcardEvent.SelectAnswerRating(UserAnswerRating.GOOD)
                        )
                              },
                    text = "GOOD",
                    color = Color.Green
                )
            } else if (state.userAnswerRating == UserAnswerRating.NONE) {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.ShowAnswer) },
                    text = "Show Answer",
                    color = Color.LightGray
                )
            } else {
                PracticeButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(PracticeFlashcardEvent.GoToNext) },
                    text = "Next",
                    color = Color.LightGray
                )
            }
        }
    }

}
