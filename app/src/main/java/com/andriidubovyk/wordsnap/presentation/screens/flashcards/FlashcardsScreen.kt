package com.andriidubovyk.wordsnap.presentation.screens.flashcards

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.components.AdvancedTextField
import com.andriidubovyk.wordsnap.presentation.navigation.Screen
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.components.FlashcardItem
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.components.FlashcardSearchBar
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.components.OrderSection
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model.FlashcardsEvent
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model.FlashcardsViewModel
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlashcardSearchBar(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    value = state.searchText,
                    onValueChange = { text -> viewModel.onEvent(FlashcardsEvent.ChangeSearchText(text)) },
                    onReset = { viewModel.onEvent(FlashcardsEvent.ResetSearch) }
                )
                IconButton(
                    onClick = { viewModel.onEvent(FlashcardsEvent.ToggleOrderSection) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary),
                    flashcardOrder = state.flashcardOrder,
                    onOrderChange = { order -> viewModel.onEvent(FlashcardsEvent.Order(order)) }
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
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
                item{ Spacer(modifier = Modifier.height(50.dp)) }
            }
        }
    }
}