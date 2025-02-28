package dev.than0s.aluminium.features.notification.framework.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getString
import dev.than0s.aluminium.R

class AdminNotification(
    private val context: Context
) {
    private val systemNotificationManager: NotificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        val existingChannel = systemNotificationManager.getNotificationChannel(ADMIN_CHANNEL_ID)
        if (existingChannel == null) {
            createChannel()
        }
    }

    private fun createChannel() {
        val name = getString(context, R.string.admin_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(ADMIN_CHANNEL_ID, name, importance)
        systemNotificationManager.createNotificationChannel(mChannel)
    }

    fun pushNotification(
        id: Int,
        title: String,
        content: String,
    ) {
        val builder = NotificationCompat.Builder(context, ADMIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }

            notify(id, builder.build())
        }
    }

    companion object {
        const val ADMIN_CHANNEL_ID = "admin_notification_channel"
    }
}