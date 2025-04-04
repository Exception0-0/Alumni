package dev.than0s.aluminium.features.notification.presentation.push_notification

import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification

data class StateScreenPushNotification(
    val isLoading: Boolean = false,
    val title: String = "",
    val content: String = "",
    val pushNotification: PushNotification = PushNotification(),
    val fromExpanded: Boolean = false,
    val toExpanded: Boolean = false,
)
