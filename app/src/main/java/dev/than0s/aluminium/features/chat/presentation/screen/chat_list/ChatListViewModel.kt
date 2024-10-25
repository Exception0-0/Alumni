package dev.than0s.aluminium.features.chat.presentation.screen.chat_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.chat.domain.use_case.GetCurrentChatList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    val getCurrentChatList: GetCurrentChatList
) : ViewModel() {
    var chatList by mutableStateOf(emptyList<String>())
        private set

    init {
        loadChatList()
    }

    fun loadChatList() {
        viewModelScope.launch {
            when (val result = getCurrentChatList.invoke(Unit)) {
                is Either.Left -> SnackbarController.showSnackbar(result.value.message)
                is Either.Right -> {
                    chatList = result.value
                }
            }
        }
    }
}