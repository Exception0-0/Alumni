package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAnimatedVisibility
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFloatingActionButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFullScreen
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredNoData
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerProfileImage
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextWidth
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.UserProfile
import dev.than0s.aluminium.core.presentation.utils.UserProfile.getUser
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
        val groupList = state.groupList.collectAsState(null).value
        PreferredAnimatedVisibility(
            visible = groupList == null,
        ) {
            ShimmerList()
        }
        PreferredAnimatedVisibility(
            visible = groupList != null && groupList.isEmpty(),
        ) {
            PreferredNoData(
                title = "No Chats",
                description = "click to new message button to chat with friends",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        PreferredAnimatedVisibility(
            visible = !groupList.isNullOrEmpty(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize()
            ) {
                items(groupList!!) { item ->
                    if (!UserProfile.userMap.containsKey(item.receiverId)) {
                        UserProfile.userMap.getUser(item.receiverId)
                    }
                    val user = UserProfile.userMap[item.receiverId] ?: User()
                    GroupItem(
                        user = user,
                        messageId = item.messageId,
                        state = state,
                        onEvent = onEvent,
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
        if (state.newMessageVisibility) {
            NewMessage(
                openScreen = openScreen,
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun GroupItem(
    user: User,
    messageId: String,
    state: StateGroupList,
    onEvent: (EventsGroupList) -> Unit,
    onClick: () -> Unit,
) {
    LaunchedEffect(state.lastMessageMap[user.id]) {
        onEvent(
            EventsGroupList.GetChatMessage(
                receiverId = user.id,
                messageId = messageId
            )
        )
    }
    LaunchedEffect(state.lastSeenMap[user.id]) {
        onEvent(
            EventsGroupList.GetLastSeen(
                userId = user.id
            )
        )
    }

    val message = state.lastMessageMap[user.id]
    val userStatus = state.lastSeenMap[user.id]?.collectAsState(null)?.value

    ListItem(
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontSize = MaterialTheme.textSize.medium,
                fontWeight = FontWeight.Bold
            )
        },
        leadingContent = {
            Box {
                PreferredAsyncImage(
                    model = user.profileImage,
                    contentDescription = "user profile image",
                    shape = CircleShape,
                    modifier = Modifier.size(MaterialTheme.profileSize.medium)
                )
                PreferredAnimatedVisibility(
                    visible = userStatus != null && userStatus.isOnline,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Image(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = "user status",
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        },
        supportingContent = {
            PreferredAnimatedVisibility(message != null) {
                Text(
                    text = message!!.message,
                    fontSize = MaterialTheme.textSize.medium,
                )
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun NewMessage(
    openScreen: (Screen) -> Unit,
    state: StateGroupList,
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
                val userStatus = state.lastSeenMap[it.id]?.collectAsState(null)?.value
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
                    trailingContent = {
                        PreferredAnimatedVisibility(userStatus != null) {
                            val color = if (userStatus!!.isOnline) Color.Green else Color.Red

                            Image(
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "user status",
                                colorFilter = ColorFilter.tint(color = color),
                                modifier = Modifier.size(12.dp)
                            )
                        }
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

@Composable
private fun ShimmerList() {
    PreferredColumn(
        verticalArrangement = Arrangement.Top
    ) {
        for (i in 1..10) {
            ShimmerListItem()
        }
    }
}

@Composable
private fun ShimmerListItem() {
    ListItem(
        headlineContent = {
            ShimmerText(
                width = ShimmerTextWidth.medium
            )
        },
        supportingContent = {
            PreferredColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(
                    modifier = Modifier.height(MaterialTheme.padding.extraSmall)
                )
                ShimmerText(
                    width = ShimmerTextWidth.high
                )
            }
        },
        leadingContent = {
            ShimmerProfileImage()
        },
        modifier = Modifier.shimmer()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {

}