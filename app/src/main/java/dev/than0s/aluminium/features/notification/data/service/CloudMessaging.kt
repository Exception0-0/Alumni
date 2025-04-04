package dev.than0s.aluminium.features.notification.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCaseSetToken
import dev.than0s.aluminium.features.notification.framework.notification.AdminNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CloudMessaging : FirebaseMessagingService() {
    private lateinit var notification: AdminNotification

    @Inject
    lateinit var useCaseSetToken: UseCaseSetToken

    override fun onCreate() {
        super.onCreate()
        notification = AdminNotification(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            useCaseSetToken.invoke(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notification.pushNotification(
            id = remoteMessage.sentTime.toInt(),
            title = remoteMessage.notification?.title ?: "",
            content = remoteMessage.notification?.body ?: ""
        )
    }
}