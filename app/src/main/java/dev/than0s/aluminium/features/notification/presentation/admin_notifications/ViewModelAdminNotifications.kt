package dev.than0s.aluminium.features.notification.presentation.admin_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCaseGetPushNotifications
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAdminNotifications @Inject constructor(
    private val getPushNotifications: UseCaseGetPushNotifications,
) : ViewModel() {
    var state by mutableStateOf(StateAdminNotifications())

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = getPushNotifications.invoke()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(notificationList = result.data!!)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    fun onEvent(event: EventsAdminNotifications) {
        when (event) {
            is EventsAdminNotifications.GetNotifications -> getNotifications()
        }
    }
}