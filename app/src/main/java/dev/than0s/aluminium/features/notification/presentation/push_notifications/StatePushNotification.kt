package dev.than0s.aluminium.features.notification.presentation.push_notifications

import dev.than0s.aluminium.core.domain.error.PreferredError
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification

data class StatePushNotification(
    val isLoading: Boolean = false,
    val notification: PushNotification = PushNotification(),
    val titleError: PreferredError? = null,
    val bodyError: PreferredError? = null,
    val targetError: PreferredError? = null,
    val alumniFilterError: PreferredError? = null,
    val alumniBatchError: PreferredError? = null,
    val studentFilterError: PreferredError? = null,
    val studentBatchError: PreferredError? = null,
    val pageIndex: Int = 0,
)