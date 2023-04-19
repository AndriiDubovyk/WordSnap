package com.andriidubovyk.wordsnap.presentation.screens.account

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountAction
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountEvent
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountState
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountViewModel
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.ui.AccountViewProfile
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.ui.AccountViewSignIn
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    // Check if the user is already signed in
    LaunchedEffect(key1 = Unit) {
        val signedInUser = googleAuthUiClient.getSignedInUser()
        if(signedInUser != null) {
            viewModel.onEvent(AccountEvent.SignIn(signedInUser))
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onEvent(AccountEvent.GetSignInResult(signInResult))
                }
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.actionFlow.collectLatest { action ->
            when(action) {
                is AccountAction.SignInClick -> {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
                is AccountAction.SignOut -> {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    when (val state = viewModel.state.value) {
        is AccountState.SignIn -> AccountViewSignIn(
            state = state,
            onEvent = { viewModel.onEvent(it) }
        )
        is AccountState.Profile -> AccountViewProfile(
            state = state,
            onEvent = { viewModel.onEvent(it) }
        )
    }

}