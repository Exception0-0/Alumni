package dev.than0s.aluminium.features.chat.presentation.screen.chat_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import dev.than0s.aluminium.features.chat.domain.use_case.AddChatUseCase
import dev.than0s.aluminium.features.chat.domain.use_case.GetReceiverChatFlowUseCase
import dev.than0s.aluminium.features.chat.domain.use_case.GetSenderChatFlowUseCase
import dev.than0s.aluminium.features.chat.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.features.chat.domain.use_case.RemoveChatUseCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getReceiverChatFlowUseCase: GetReceiverChatFlowUseCase,
    private val getSenderChatFlowUseCase: GetSenderChatFlowUseCase,
    private val addChatUseCase: AddChatUseCase,
    private val removeChatUseCase: RemoveChatUseCase,
    private val getUserProfileUseCase: GetUserUseCase,
) : ViewModel() {
    private val profileScreenArgs = savedStateHandle.toRoute<Screen.ChatDetailScreen>()
    var receiverChatFlow by mutableStateOf(emptyFlow<List<Chat>>())
        private set
    var senderChatFlow by mutableStateOf(emptyFlow<List<Chat>>())
        private set
    var currentChat by mutableStateOf(getNewChat())
        private set
    var user by mutableStateOf(User())
        private set

    init {
        loadChatFlow()
        getUser()
    }

    private fun getNewChat(): Chat {
        return Chat(receiverId = profileScreenArgs.userId)
    }

    private fun loadChatFlow() {
        viewModelScope.launch {
            when (val result = getReceiverChatFlowUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Right -> receiverChatFlow = result.value
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
            when (val result = getSenderChatFlowUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Right -> senderChatFlow = result.value
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            when (val result = getUserProfileUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Left -> SnackbarController.showSnackbar(result.value.message)
                is Either.Right -> user = result.value
            }
        }
    }

    fun onChatMessageChange(message: String) {
        currentChat = currentChat.copy(message = message)
    }

    fun onSendClick(onFinish: () -> Unit) {
        viewModelScope.launch {
            when (val result = addChatUseCase.invoke(currentChat)) {
                is Either.Left -> SnackbarController.showSnackbar(result.value.message)
                is Either.Right -> {
                    currentChat = getNewChat()
                }
            }
            onFinish()
        }
    }

    fun onChatRemoveClick(chat: Chat, onFinish: () -> Unit) {
        viewModelScope.launch {
            when (val result = removeChatUseCase.invoke(chat)) {
                is Either.Left -> SnackbarController.showSnackbar(result.value.message)
                is Either.Right -> {
                    SnackbarController.showSnackbar("Chat remove successfully")
                }
            }
            onFinish()
        }
    }
}