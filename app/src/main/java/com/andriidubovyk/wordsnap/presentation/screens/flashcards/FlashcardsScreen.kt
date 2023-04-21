package com.andriidubovyk.wordsnap.presentation.screens.flashcards

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.components.FlashcardItem
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model.FlashcardsEvent
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model.FlashcardsViewModel
import com.andriidubovyk.wordsnap.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    navController: NavController,
    viewModel: FlashcardsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
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
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_flashcard))
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item{ Spacer(modifier = Modifier.height(10.dp)) }
                items(state.flashcards) { flashcard ->
                    FlashcardItem(
                        flashcard = flashcard,
                        onClick = {
                            navController.navigate(Screen.AddEditFlashcard.route + "?flashcardId=${flashcard.id}")
                        },
                        onDeleteClick = {
                            viewModel.onEvent(FlashcardsEvent.DeleteFlashcard(flashcard))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = context.getString((R.string.flashcard_deleted)),
                                    actionLabel = context.getString((R.string.undo))
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(FlashcardsEvent.RestoreFlashcard)
                                }
                            }
                        }
                    )
                }
                item{ Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
    }
}