package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFlashcardScreen(
    navController: NavController,
    viewModel: AddEditFlashcardViewModel = hiltViewModel()
) {
    val wordState = viewModel.flashcardWord.value
    val definitionState = viewModel.flashcardDefinition.value
    val translationState = viewModel.flashcardTranslation.value
    val onlineDefinitionsDialogState = viewModel.onlineDefinitionsDialog.value

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditFlashcardViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditFlashcardViewModel.UiEvent.SaveFlashcard -> {
                    navController.navigateUp()
                }
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
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save flashcard")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransparentHintTextField(
                text = wordState.text,
                hint = wordState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.EnterWord(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.ChangeWordFocus(it))
                },
                isHintVisible = wordState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            TransparentHintTextField(
                text = definitionState.text,
                hint = definitionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.EnterDefinition(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.ChangeDefinitionFocus(it))
                },
                isHintVisible = definitionState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Button(
                onClick = {
                    viewModel.onEvent(AddEditFlashcardEvent.GetDefinitionsFromDictionary)
                }
            ) {
                Text(
                    text = "Get from online dictionary",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            TransparentHintTextField(
                text = translationState.text,
                hint = translationState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.EnterTranslation(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashcardEvent.ChangeTranslationFocus(it))
                },
                isHintVisible = translationState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )

            if(onlineDefinitionsDialogState.isOpen) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.onEvent(AddEditFlashcardEvent.CloseDefinitonsDialog)
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text ="Select definition",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Divider(thickness = 2.dp)
                        LazyColumn {
                            items(onlineDefinitionsDialogState.definitions) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.onEvent(
                                                AddEditFlashcardEvent.SelectDefinitionFromDialog(it)
                                            )
                                        }
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }

    }
}