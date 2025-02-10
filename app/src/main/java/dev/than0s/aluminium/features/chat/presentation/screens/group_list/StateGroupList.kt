package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import dev.than0s.aluminium.features.chat.domain.data_class.ChatGroup
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StateGroupList(
    val newMessageVisibility: Boolean = false,
    val groupList: Flow<List<ChatGroup>> = emptyFlow(),
    val lastMessageMap: SnapshotStateMap<String, ChatMessage> = mutableStateMapOf()
)
