package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StateGroupList(
    val isLoading: Boolean = false,
    val newMessageVisibility: Boolean = false,
    val groupList: Flow<List<ChatGroup>> = emptyFlow(),
)
