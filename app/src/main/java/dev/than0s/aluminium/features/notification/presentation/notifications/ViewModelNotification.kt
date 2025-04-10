package dev.than0s.aluminium.features.notification.presentation.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCaseGetNotifications
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCaseRemoveNotification
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelNotification @Inject constructor(
    private val useCaseRemoveNotification: UseCaseRemoveNotification,
    private val useCaseGetNotifications: UseCaseGetNotifications
) : ViewModel() {
    var state by mutableStateOf(StateNotification())

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = useCaseGetNotifications.invoke()) {
                is Resource.Error -> {
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError(),
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.try_again),
                            action = {
                                getNotifications()
                            }
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(notifications = result.data!!)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun removeNotification(notification: CloudNotification) {
        viewModelScope.launch {
            state = state.copy(isDeleting = true)
            when (val result = useCaseRemoveNotification.invoke(notification = notification)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    removeNotification(notification)
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.notification_removed),
                        )
                    )
                }
            }
            state = state.copy(isDeleting = false)
        }
    }

    fun onEvent(event: EventsNotification) {
        when (event) {
            is EventsNotification.RemoteNotification -> removeNotification(event.notification)
            is EventsNotification.GetNotifications -> getNotifications()
        }
    }
}