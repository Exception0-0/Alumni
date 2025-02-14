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
import dev.than0s.aluminium.core.domain.use_case.UseCaseGetUserStatus
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseAddMessage
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseClearAllChat
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseDeleteMessage
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseGetMessages
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDetailChat @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCaseGetMessages: UseCaseGetMessages,
    private val useCaseAddMessage: UseCaseAddMessage,
    private val getUserUseCase: GetUserUseCase,
    private val useCaseGetUserStatus: UseCaseGetUserStatus,
    private val useCaseDeleteMessage: UseCaseDeleteMessage,
    private val useCaseClearAllChat: UseCaseClearAllChat
) : ViewModel() {
    private val args = savedStateHandle.toRoute<Screen.ChatDetailScreen>()
    var state by mutableStateOf(StateDetailChat())

    init {
        loadMessages()
        loadUser()
        loadUserStatus()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            when (val result = useCaseGetMessages(receiverId = args.receiverId)) {
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
            when (val result = getUserUseCase(args.receiverId)) {
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

    private fun loadUserStatus() {
        viewModelScope.launch {
            when (val result = useCaseGetUserStatus(userId = args.receiverId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    loadUserStatus()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        userStatus = result.data!!
                    )
                }
            }
        }
    }

    private fun addMessage() {
        viewModelScope.launch {
            state = state.copy(isSending = true)
            val addMessageResult = useCaseAddMessage(
                receiverId = args.receiverId,
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

    private fun deleteMessage() {
        viewModelScope.launch {
            state = state.copy(isDeleting = true)
            when (val result =
                useCaseDeleteMessage(
                    receiverId = args.receiverId,
                    messageId = state.deleteDialog!!
                )) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    deleteMessage()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarEvent(
                        message = UiText.StringResource(R.string.message_deleted)
                    )
                }
            }
            state = state.copy(isDeleting = false)
            dismissDeleteDialog()
        }
    }

    private fun showDeleteDialog(messageId: String) {
        state = state.copy(deleteDialog = messageId)
    }

    private fun dismissDeleteDialog() {
        state = state.copy(deleteDialog = null)
    }

    private fun clearAllChat() {
        viewModelScope.launch {
            state = state.copy(isDeleting = true)
            when (val result = useCaseClearAllChat(receiverId = args.receiverId)) {
                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.clear_all_chat)
                        )
                    )
                }

                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    clearAllChat()
                                }
                            )
                        )
                    )
                }
            }
            state = state.copy(isDeleting = false)
            dismissClearAllChatDialog()
        }
    }

    private fun showClearAllChatDialog() {
        state = state.copy(clearAllChatDialog = true)
    }

    private fun dismissClearAllChatDialog() {
        state = state.copy(clearAllChatDialog = false)
    }

    private fun changeTopDropDownState() {
        state = state.copy(topDropDownMenu = !state.topDropDownMenu)
    }

    fun onEvent(event: EventsDetailChat) {
        when (event) {
            is EventsDetailChat.AddMessage -> addMessage()
            is EventsDetailChat.LoadMessages -> loadMessages()
            is EventsDetailChat.OnMessageChange -> onMessageChange(event.message)
            is EventsDetailChat.DeleteMessage -> deleteMessage()
            is EventsDetailChat.DismissDeleteDialog -> dismissDeleteDialog()
            is EventsDetailChat.ShowDeleteDialog -> showDeleteDialog(event.messageId)
            is EventsDetailChat.DismissClearAllChatDialog -> dismissClearAllChatDialog()
            is EventsDetailChat.ShowClearAllChatDialog -> showClearAllChatDialog()
            is EventsDetailChat.ClearAllChat -> clearAllChat()
            is EventsDetailChat.ChangeTopDropDownState -> changeTopDropDownState()
        }
    }
}