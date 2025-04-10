package dev.than0s.aluminium.features.notification.presentation.notifications

import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification

data class StateNotification(
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val notifications: List<CloudNotification> = emptyList()
)
