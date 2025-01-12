package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseAddMessage
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseGetMessages
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDetailChat @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCaseGetMessages: UseCaseGetMessages,
    private val useCaseAddMessage: UseCaseAddMessage,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<Screen.ChatDetailScreen>()
    var state by mutableStateOf(StateDetailChat())

    init{
        loadMessages()
        loadUser()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            when (val result = useCaseGetMessages(groupId = args.groupId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    loadMessages()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(chatFlow = result.data!!)
                }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = getUserUseCase(args.userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    loadUser()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(otherUser = result.data!!)
                }
            }
            state = state.copy(isLoading = true)
        }
    }

    private fun addMessage() {
        viewModelScope.launch {
            state = state.copy(isSending = true)
            val addMessageResult = useCaseAddMessage(
                groupId = args.groupId,
                message = ChatMessage(message = state.chatMessage)
            )
            if (addMessageResult.messageError != null) {
                state = state.copy(messageError = addMessageResult.messageError)
            }
            when (addMessageResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = addMessageResult.result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    addMessage()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(chatMessage = "")
                }

                null -> {}
            }
            state = state.copy(isSending = false)
        }
    }

    private fun onMessageChange(message: String) {
        state = state.copy(chatMessage = message)
    }

    fun onEvent(event: EventsDetailChat) {
        when (event) {
            is EventsDetailChat.AddMessage -> addMessage()
            is EventsDetailChat.LoadMessages -> loadMessages()
            is EventsDetailChat.OnMessageChange -> onMessageChange(event.message)
        }
    }
}