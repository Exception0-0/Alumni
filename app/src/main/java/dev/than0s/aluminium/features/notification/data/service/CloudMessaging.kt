package dev.than0s.aluminium.features.notification.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.than0s.aluminium.features.notification.data.notification.Notification

class CloudMessaging : FirebaseMessagingService() {
    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        notification = Notification(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notification.pushNotification(
            id = remoteMessage.sentTime.toInt(),
            title = remoteMessage.notification?.title ?: "Nothing to say",
            content = remoteMessage.notification?.body ?: "Nothing to say"
        )
    }
}