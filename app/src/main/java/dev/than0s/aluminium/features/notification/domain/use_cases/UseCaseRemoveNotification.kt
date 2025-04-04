package dev.than0s.aluminium.features.notification.domain.use_cases

import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCaseRemoveNotification @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(notification: CloudNotification) =
        repository.remoteNotification(notification)
}