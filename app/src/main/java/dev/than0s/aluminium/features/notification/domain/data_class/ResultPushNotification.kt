package dev.than0s.aluminium.features.notification.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.error.PreferredError

data class ResultPushNotification(
    val titleError: PreferredError? = null,
    val bodyError: PreferredError? = null,
    val targetError: PreferredError? = null,
    val alumniFilterError: PreferredError? = null,
    val alumniBatchError: PreferredError? = null,
    val studentFilterError: PreferredError? = null,
    val studentBatchError: PreferredError? = null,
    val result: SimpleResource? = null
)
