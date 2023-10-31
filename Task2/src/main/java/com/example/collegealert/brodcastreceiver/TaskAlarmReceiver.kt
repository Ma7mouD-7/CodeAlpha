package com.example.collegealert.brodcastreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.collegealert.R
import com.example.collegealert.ui.main.view.MainActivity
import com.example.collegealert.utils.Constants.Companion.TASK_DATE
import com.example.collegealert.utils.Constants.Companion.TASK_NAME
import com.example.collegealert.utils.Constants.Companion.TASK_TIME

/*
* Created by Ma7mouD on Tue 31/10/2023 at 09:36 AM.
*/
class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra(TASK_NAME)
        val taskTime = intent.getStringExtra(TASK_TIME)
        val taskDate = intent.getStringExtra(TASK_DATE)

        val notificationId = 7107 // Unique ID for the notification
        val channelId = "task_channel" // Unique channel ID for the notification

        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
        )

        Log.d("ALAAARM", "onReceive: ALARM")

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentTitle("Task Reminder")
            .setContentText("You have $taskName at $taskDate on $taskTime")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setColor(ContextCompat.getColor(context, R.color.cyan))
            .setLargeIcon(ContextCompat.getDrawable(context, R.drawable.logo)!!.toBitmap())
            .addAction(R.drawable.cancel, "Dismiss", pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "task_channel Channel",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())

        mediaPlayer.isLooping = true
        mediaPlayer.start()

        if (intent.action == "com.example.collegealert.ACTION_BUTTON_CLICK") {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
