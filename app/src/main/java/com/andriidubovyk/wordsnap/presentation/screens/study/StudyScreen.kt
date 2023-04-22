package com.andriidubovyk.wordsnap.presentation.screens.study

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.navigation.Screen
import com.andriidubovyk.wordsnap.presentation.components.DescriptionText
import com.andriidubovyk.wordsnap.presentation.screens.study.view_model.StudyViewModel


@Composable
fun StudyScreen(
    navController: NavController,
    viewModel: StudyViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DescriptionText(
            descName = stringResource(R.string.totatl_score),
            descValue = state.totalScore.toString()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                navController.navigate(Screen.PracticeFlashcard.route)
            }
        ) {
            Text(
                text = stringResource(R.string.start),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}