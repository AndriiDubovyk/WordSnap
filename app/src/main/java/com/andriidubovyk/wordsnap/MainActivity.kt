package com.andriidubovyk.wordsnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.andriidubovyk.wordsnap.presentation.bottom_nav_bar.BottomNavigationPanel
import com.andriidubovyk.wordsnap.presentation.navigation.NavigationHost
import com.andriidubovyk.wordsnap.ui.theme.WordSnapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordSnapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationPanel(navController = navController)
                        }
                    ) {
                        NavigationHost(
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}