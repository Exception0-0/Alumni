package dev.than0s.aluminium.features.notification.domain.use_cases

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.domain.error.SimpleError
import dev.than0s.aluminium.core.domain.util.TextFieldLimits
import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.features.notification.domain.data_class.ResultPushNotification
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCasePushNotification @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(notification: PushNotification): ResultPushNotification {
        var result = ResultPushNotification()
        if (notification.content.title.isEmpty()) {
            result = result.copy(
                titleError = TextFieldError.FieldEmpty
            )
        }
        if (notification.content.content.isEmpty()) {
            result = result.copy(
                bodyError = TextFieldError.FieldEmpty
            )
        }
        notification.filters.let {
            if (!it.staff && it.alumni == null && it.student == null) {
                result = result.copy(
                    targetError = SimpleError(UiText.StringResource(R.string.least_one_target_selected))
                )
            }
            it.alumni?.let { alumni ->
                if (!alumni.mca and !alumni.mba) {
                    result = result.copy(
                        alumniFilterError = SimpleError(UiText.StringResource(R.string.least_one_filter_selected))
                    )
                }
                alumni.batch?.let { alub ->
                    if (alub.to.length != TextFieldLimits.MAX_BATCH || alub.from.length != TextFieldLimits.MAX_BATCH) {
                        result = result.copy(
                            alumniBatchError = TextFieldError.ShortYear
                        )
                    }
                }
            }
            it.student?.let { student ->
                if (!student.mca and !student.mba) {
                    result = result.copy(
                        studentFilterError = SimpleError(UiText.StringResource(R.string.least_one_filter_selected))
                    )
                }
                student.batch?.let { stub ->
                    if (stub.to.length != TextFieldLimits.MAX_BATCH || stub.from.length != TextFieldLimits.MAX_BATCH) {
                        result = result.copy(
                            studentBatchError = TextFieldError.ShortYear
                        )
                    }
                }
            }
        }
        if (result.titleError != null ||
            result.bodyError != null ||
            result.targetError != null ||
            result.alumniBatchError != null ||
            result.studentBatchError != null ||
            result.alumniFilterError != null ||
            result.studentFilterError != null
        ) {
            return result
        }
        return result.copy(
            result = repository.pushNotification(
                notification.copy(
                    id = generateUniqueId(),
                    timestamp = System.currentTimeMillis(),
                )
            )
        )
    }
}