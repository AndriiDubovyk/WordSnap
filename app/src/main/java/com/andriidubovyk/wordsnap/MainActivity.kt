package com.andriidubovyk.wordsnap

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.andriidubovyk.wordsnap.common.LoaderHelper
import com.andriidubovyk.wordsnap.presentation.components.bottom_nav_bar.BottomNavigationPanel
import com.andriidubovyk.wordsnap.presentation.components.top_bar.TopBar
import com.andriidubovyk.wordsnap.presentation.navigation.NavigationHost
import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.NotificationReceiver
import com.andriidubovyk.wordsnap.ui.theme.WordSnapTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { LoaderHelper.isLoading }
        }
        MobileAds.initialize(this)
        createNotificationChannel()
        setContent {
            WordSnapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationPanel(navController = navController) },
                        topBar = { TopBar() }
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

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NotificationReceiver.channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}