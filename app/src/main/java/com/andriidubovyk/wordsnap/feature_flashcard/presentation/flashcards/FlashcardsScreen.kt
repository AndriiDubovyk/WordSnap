package com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar.BottomNavigationPanel
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards.components.FlashcardItem
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    navController: NavController,
    viewModel: FlashcardsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditFlashcard.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add flashcard")
            }
        },
        bottomBar = {
            BottomNavigationPanel(navController)
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Your Flashcards")
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.flashcards) { flashcard ->
                    FlashcardItem(
                        flashcard = flashcard,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.AddEditFlashcard.route + "?flashcardId=${flashcard.id}")
                            },
                        onDeleteClick = {
                            viewModel.onEvent(FlashcardsEvent.DeleteFlashcard(flashcard))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Flashcard deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(FlashcardsEvent.RestoreFlashcard)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}