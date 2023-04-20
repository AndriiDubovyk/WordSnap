package com.andriidubovyk.wordsnap.presentation.screens.settings.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.andriidubovyk.wordsnap.R


class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val notificationID = 1
        const val channelID = "channel1"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.dont_forget_to_learn_new_words))
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}