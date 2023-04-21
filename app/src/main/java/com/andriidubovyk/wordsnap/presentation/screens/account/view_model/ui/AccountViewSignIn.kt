package com.andriidubovyk.wordsnap.presentation.screens.account.view_model.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountEvent
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountState

@Composable
fun AccountViewSignIn(
    state: AccountState.SignIn,
    onEvent: (AccountEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { onEvent(AccountEvent.SignInClick) }) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}