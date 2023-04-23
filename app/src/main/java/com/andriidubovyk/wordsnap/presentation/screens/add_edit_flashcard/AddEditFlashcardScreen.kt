package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.common.TestTags
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.components.FlashcardFieldWithLabel
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.components.SelectDefinitionDialog
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model.AddEditFlashcardAction
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model.AddEditFlashcardEvent
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model.AddEditFlashcardViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditFlashcardScreen(
    navController: NavController,
    viewModel: AddEditFlashcardViewModel = hiltViewModel()
) {
    val flashcardWord = viewModel.flashcardWord.value
    val flashcardDefinition = viewModel.flashcardDefinition.value
    val flashcardTranslation = viewModel.flashcardTranslation.value
    val onlineDefinitionsDialog = viewModel.onlineDefinitionsDialog.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.actionFlow.collectLatest { action ->
            when(action) {
                is AddEditFlashcardAction.ShowSnackbar -> snackbarHostState.showSnackbar(
                    message = action.message
                )
                is AddEditFlashcardAction.SaveFlashcard ->  navController.navigateUp()
            }
        }
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditFlashcardEvent.SaveFlashcard)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(R.string.save_flashcard))
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        ) {
            val fieldsPadding = 25.dp
            item {
                FlashcardFieldWithLabel(
                    modifier = Modifier.padding(fieldsPadding),
                    label = stringResource(R.string.word),
                    value = flashcardWord,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.EnterWord(it)) },
                    testTag = TestTags.WORD_TEXT_FIELD
                )
                Divider()
                FlashcardFieldWithLabel(
                    modifier = Modifier
                        .padding(
                            start = fieldsPadding,
                            top = fieldsPadding,
                            end = fieldsPadding,
                            bottom = 5.dp),
                    label = stringResource(R.string.definition),
                    value = flashcardDefinition,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.EnterDefinition(it)) },
                    lines = 3,
                    testTag = TestTags.DEFINITION_TEXT_FIELD
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(fieldsPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(AddEditFlashcardEvent.GetDefinitionsFromDictionary)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.dictionary),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Divider()
                FlashcardFieldWithLabel(
                    modifier = Modifier.padding(fieldsPadding),
                    label = stringResource(R.string.translation),
                    value = flashcardTranslation,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.EnterTranslation(it)) },
                    testTag = TestTags.TRANSLATION_TEXT_FIELD
                )
            }
        }

        if(onlineDefinitionsDialog.isOpen) {
            SelectDefinitionDialog(
                definitions = onlineDefinitionsDialog.definitions,
                onSelect =  {
                    viewModel.onEvent(AddEditFlashcardEvent.SelectDefinitionFromDialog(it))
                },
                onCancel = {
                    viewModel.onEvent(AddEditFlashcardEvent.CloseDefinitionsDialog)
                }
            )
        }

    }
}