package com.andriidubovyk.wordsnap.presentation.screens.account.view_model.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.components.ConfirmDialog
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountEvent
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountState

@Composable
fun AccountViewProfile(
    state: AccountState.Profile,
    onEvent: (AccountEvent) -> Unit
) {
    var isBackupDataDialogOpened by remember { mutableStateOf(false) }
    var isRestoreDataDialogOpened by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(state.userData?.profilePictureUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                )  {
                    AsyncImage(
                        model = state.userData.profilePictureUrl,
                        contentDescription = stringResource(R.string.profile_picture),
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if(state.userData?.username != null) {
                        Text(
                            text = state.userData.username,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    Button(onClick = { onEvent(AccountEvent.SignOut) }) {
                        Text(
                            text = stringResource(R.string.sign_out),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
        Column {
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isBackupDataDialogOpened = true }
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.backup_flashcard_to_the_cloud),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isRestoreDataDialogOpened = true }
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.restore_flashcard_from_the_cloud),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    if(isBackupDataDialogOpened) {
        ConfirmDialog(
            text = stringResource(R.string.before_backup_message),
            onConfirm = { onEvent(AccountEvent.BackupFlashcardsToCloud) },
            onCancel = { isBackupDataDialogOpened = false }
        )
    }
    if(isRestoreDataDialogOpened) {
        ConfirmDialog(
            text = stringResource(R.string.before_restore_message),
            onConfirm = { onEvent(AccountEvent.RestoreFlashcardsFromCloud) },
            onCancel = { isRestoreDataDialogOpened = false }
        )
    }
}