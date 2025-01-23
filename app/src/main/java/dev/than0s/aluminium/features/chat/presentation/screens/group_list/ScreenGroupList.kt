package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFloatingActionButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFullScreen
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredNoData
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.UserProfile
import dev.than0s.aluminium.core.presentation.utils.UserProfile.getUser
import dev.than0s.aluminium.features.chat.domain.data_class.ChatMessage
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
import dev.than0s.aluminium.ui.textSize

@Composable
fun ScreenGroupList(
    viewModel: ViewModelGroupList = hiltViewModel(),
    openScreen: (Screen) -> Unit,
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        openScreen = openScreen
    )
}

@Composable
private fun Content(
    state: StateGroupList,
    onEvent: (EventsGroupList) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val groupList = state.groupList.collectAsState(emptyList()).value
        if (groupList.isEmpty()) {
            PreferredNoData(
                title = "No Chats",
                description = "do some chatting with friends"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize()
            ) {
                items(groupList) { item ->
                    if (!UserProfile.userMap.containsKey(item.receiverId)) {
                        UserProfile.userMap.getUser(item.receiverId)
                    }
                    val user = UserProfile.userMap[item.receiverId] ?: User()
                    GroupItem(
                        user = user,
                        message = item.message,
                        onClick = {
                            openScreen(
                                Screen.ChatDetailScreen(
                                    receiverId = item.receiverId
                                )
                            )
                        }
                    )
                }
            }
        }
        PreferredFloatingActionButton(
            onClick = {
                onEvent(EventsGroupList.OnNewMessageClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Default.AddComment,
                    contentDescription = "new message"
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.padding.medium)
        )
    }
    if (state.newMessageVisibility) {
        NewMessage(
            openScreen = openScreen,
            onEvent = onEvent
        )
    }
}

@Composable
private fun GroupItem(
    user: User,
    message: ChatMessage,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontSize = MaterialTheme.textSize.medium,
                fontWeight = FontWeight.Bold
            )
        },
        leadingContent = {
            PreferredAsyncImage(
                model = user.profileImage,
                contentDescription = "user profile image",
                shape = CircleShape,
                modifier = Modifier.size(MaterialTheme.profileSize.medium)
            )
        },
        supportingContent = {
            Text(
                text = message.message,
                fontSize = MaterialTheme.textSize.medium,
            )
        },
        trailingContent = {
            Text(
                text = PrettyTimeUtils.getPrettyTime(message.timestamp),
                fontSize = MaterialTheme.textSize.medium
            )
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun NewMessage(
    openScreen: (Screen) -> Unit,
    onEvent: (EventsGroupList) -> Unit,
) {
    PreferredFullScreen(
        title = "New message",
        onDismissRequest = {
            onEvent(EventsGroupList.OnNewMessageClick)
        }
    ) {
        LazyColumn {
            items(UserProfile.userMap.values.toList()) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "${it.firstName} ${it.lastName}",
                            fontSize = MaterialTheme.textSize.medium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    leadingContent = {
                        PreferredAsyncImage(
                            model = it.profileImage,
                            contentDescription = "user profile image",
                            shape = CircleShape,
                            modifier = Modifier.size(MaterialTheme.profileSize.medium)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.bio,
                            fontSize = MaterialTheme.textSize.medium,
                        )
                    },
                    modifier = Modifier.clickable {
                        openScreen(
                            Screen.ChatDetailScreen(
                                receiverId = it.id
                            )
                        )
                        onEvent(EventsGroupList.OnNewMessageClick)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {

}