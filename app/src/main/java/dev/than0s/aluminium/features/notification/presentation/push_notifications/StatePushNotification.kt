package dev.than0s.aluminium.features.notification.presentation.push_notifications

import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification

data class StatePushNotification(
    val isLoading: Boolean = false,
    val notification: PushNotification = PushNotification(),
    val pageIndex: Int = 0,
)