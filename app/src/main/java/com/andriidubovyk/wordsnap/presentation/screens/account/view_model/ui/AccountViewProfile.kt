package com.andriidubovyk.wordsnap.presentation.screens.account.view_model.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountEvent
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.AccountState

@Composable
fun AccountViewProfile(
    state: AccountState.Profile,
    onEvent: (AccountEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state.userData?.profilePictureUrl != null) {
            AsyncImage(
                model = state.userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(state.userData?.username != null) {
            Text(
                text = state.userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = { onEvent(AccountEvent.SignOut) }) {
            Text(text = "Sign out")
        }
    }
}