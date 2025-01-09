package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseGetGroups
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelGroupList @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val useCaseGetGroups: UseCaseGetGroups
) : ViewModel() {
    var state by mutableStateOf(StateGroupList())
    val userMap = mutableStateMapOf<String, User>()

    init {
        loadChatGroup()
    }

    private fun loadChatGroup() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            when (val result = useCaseGetGroups()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError(),
                            action = SnackbarAction(
                                name = UiText.StringResource(R.string.try_again),
                                action = {
                                    loadChatGroup()
                                }
                            )
                        )
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        groupList = result.data!!.map {
                            it.copy(usersId = it.usersId.minus(currentUserId!!))
                        }
                    )
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun SnapshotStateMap<String, User>.getUser(userId: String) {
        if (!containsKey(userId)) {
            viewModelScope.launch {
                when (val result = getUserUseCase(userId)) {
                    is Resource.Error -> {
                        // TODO: do something on error
                    }

                    is Resource.Success -> {
                        this@getUser[userId] = result.data!!
                    }
                }
            }
        }
    }

    fun onEvent(event: EventsGroupList) {
        when (event) {
            is EventsGroupList.LoadGroup -> loadChatGroup()
            is EventsGroupList.LoadUser -> userMap.getUser(event.userId)
        }
    }
}