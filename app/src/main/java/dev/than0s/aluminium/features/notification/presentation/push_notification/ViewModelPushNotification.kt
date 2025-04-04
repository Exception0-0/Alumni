package dev.than0s.aluminium.features.notification.presentation.push_notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelPushNotification @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(StateScreenPushNotification())

    private fun changeTitle(title: String) {
        state = state.copy(title = title)
    }

    private fun changeContent(content: String) {
        state = state.copy(content = content)
    }

    private fun changeFilterMca() {
        state = state.copy(
            pushNotification = state.pushNotification.copy(
                categories = state.pushNotification.categories.copy(
                    mca = !state.pushNotification.categories.mca
                )
            )
        )
    }

    fun onEvent(event: EventsPushNotification) {
        when (event) {
            is EventsPushNotification.ChangeContent -> changeTitle(event.content)
            is EventsPushNotification.ChangeTitle -> changeContent(event.title)
            EventsPushNotification.PushNotification -> TODO()
        }
    }
}