package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup

data class StateGroupList(
    val isLoading: Boolean = false,
    val groupList: List<ChatGroup> = emptyList(),
)
