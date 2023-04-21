package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andriidubovyk.wordsnap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDefinitionDialog(
    modifier: Modifier = Modifier,
    definitions: List<String>,
    onSelect: (String) -> Unit,
    onCancel: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    AlertDialog(
        modifier = modifier
            .heightIn(min = 210.dp, max = 600.dp)
            .width(280.dp)
            .clip(shape)
            .border(1.dp, DividerDefaults.color, shape)
            .background(MaterialTheme.colorScheme.background),
        onDismissRequest = { onCancel() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.select_definition),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Divider()
            LazyColumn {
                items(definitions) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(it) }
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