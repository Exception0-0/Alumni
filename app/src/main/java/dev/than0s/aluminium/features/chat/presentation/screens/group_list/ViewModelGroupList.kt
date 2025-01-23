package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.use_case.UseCaseGetAllUserProfile
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.presentation.utils.UserProfile
import dev.than0s.aluminium.features.chat.domain.use_case.UseCaseGetGroups
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelGroupList @Inject constructor(
    private val useCaseGetGroups: UseCaseGetGroups,
    private val useCaseGetAllUserProfile: UseCaseGetAllUserProfile,
) : ViewModel() {
    var state by mutableStateOf(StateGroupList())

    init {
        loadChatGroup()
        loadAllUserProfile()
    }

    private fun loadChatGroup() {
        viewModelScope.launch {
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
                        groupList = result.data!!
                    )
                }
            }
        }
    }

    private fun loadAllUserProfile() {
        viewModelScope.launch {
            when (val result = useCaseGetAllUserProfile()) {
                is Resource.Error -> {
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError(),
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.try_again),
                            action = {
                                loadAllUserProfile()
                            }
                        )
                    )
                }

                is Resource.Success -> {
                    result.data!!.forEach { user ->
                        UserProfile.userMap[user.id] = user
                    }
                }
            }
        }
    }

    private fun changeNewMessageState() {
        state = state.copy(newMessageVisibility = !state.newMessageVisibility)
    }

    fun onEvent(event: EventsGroupList) {
        when (event) {
            is EventsGroupList.LoadGroup -> loadChatGroup()
            EventsGroupList.OnNewMessageClick -> changeNewMessageState()
        }
    }
}