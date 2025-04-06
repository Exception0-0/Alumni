package dev.than0s.aluminium.features.notification.domain.use_cases

import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCasePushNotification @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(notification: PushNotification) =
        repository.pushNotification(
            notification.copy(
                id = generateUniqueId()
            )
        )
}