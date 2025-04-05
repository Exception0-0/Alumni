package dev.than0s.aluminium.features.notification.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.than0s.aluminium.features.notification.framework.notification.AdminNotification

class CloudMessaging : FirebaseMessagingService() {
    private lateinit var notification: AdminNotification

    override fun onCreate() {
        super.onCreate()
        notification = AdminNotification(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notification.pushNotification(
            id = remoteMessage.sentTime.toInt(),
            title = remoteMessage.notification?.title ?: "",
            content = remoteMessage.notification?.body ?: ""
        )
    }
}