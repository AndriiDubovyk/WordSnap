package com.andriidubovyk.wordsnap.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.andriidubovyk.wordsnap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    text: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    AlertDialog(
        modifier = modifier
            .height(210.dp)
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
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = text, style = MaterialTheme.typography.bodyLarge)
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { onCancel() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { onConfirm() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}