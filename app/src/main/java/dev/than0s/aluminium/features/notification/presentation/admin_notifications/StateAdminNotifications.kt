package dev.than0s.aluminium.features.notification.presentation.admin_notifications

import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification

data class StateAdminNotifications(
    val isLoading: Boolean = false,
    val notificationList: List<PushNotification> = emptyList(),
)