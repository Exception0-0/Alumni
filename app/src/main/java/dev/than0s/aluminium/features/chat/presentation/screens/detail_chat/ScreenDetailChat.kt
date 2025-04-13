package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.util.TextFieldLimits
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAnimatedVisibility
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredIconButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredNoData
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
import dev.than0s.aluminium.ui.textSize

@Composable
fun ScreenDetailChat(
    viewModel: ViewModelDetailChat = hiltViewModel(),
    popScreen: () -> Unit,
    openScreen: (Screen) -> Unit,
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
        popScreen = popScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: StateDetailChat,
    openScreen: (Screen) -> Unit,
    onEvent: (EventsDetailChat) -> Unit,
    popScreen: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                UserDetail(
                    user = state.otherUser,
                    state = state,
                    openScreen = openScreen,
                )
            },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    },
                    onClick = popScreen
                )
            },
            actions = {
                Box {
                    IconButton(
                        onClick = {
                            onEvent(EventsDetailChat.ChangeTopDropDownState)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "more"
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = state.topDropDownMenu,
                        onDismissRequest = {
                            onEvent(EventsDetailChat.ChangeTopDropDownState)
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Profile")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile"
                                )
                            },
                            onClick = {
                                openScreen(Screen.ProfileScreen(userId = state.otherUser.id))
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text("Clear")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Clear"
                                )
                            },
                            onClick = {
                                onEvent(EventsDetailChat.ShowClearAllChatDialog)
                            }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        val chatList = state.chatFlow.collectAsState(null).value
        if (chatList != null) {
            if (chatList.isEmpty()) {
                PreferredNoData(
                    title = stringResource(R.string.empty_chat),
                    description = "Say hi to \"${state.otherUser.firstName} ${state.otherUser.lastName}\"",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.Bottom,
                    contentPadding = PaddingValues(
                        bottom = TextFieldDefaults.MinHeight,
                        top = TopAppBarDefaults.TopAppBarExpandedHeight
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(chatList) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(MaterialTheme.padding.verySmall)
                        ) {
                            PreferredSurface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .align(
                                        if (item.userId == currentUserId!!) Alignment.TopEnd
                                        else Alignment.TopStart
                                    )
                                    .clickable {
                                        onEvent(
                                            EventsDetailChat.ShowDeleteDialog(
                                                messageId = item.id
                                            )
                                        )
                                    },
                                content = {
                                    Text(
                                        text = buildAnnotatedString {
                                            pushStyle(SpanStyle(fontSize = MaterialTheme.textSize.large))
                                            append(item.message)
                                            pop()
                                            append(MESSAGE_TIME_SPACING)
                                            pushStyle(SpanStyle(fontSize = MaterialTheme.textSize.medium))
                                            append(PrettyTimeUtils.getFormatedTime(item.timestamp))
                                        },
                                        fontSize = MaterialTheme.textSize.medium,
                                        modifier = Modifier.padding(MaterialTheme.padding.small)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        PreferredTextField(
            placeholder = "message",
            value = state.chatMessage,
            onValueChange = {
                onEvent(EventsDetailChat.OnMessageChange(it))
            },
            maxChar = TextFieldLimits.MAX_MESSAGE,
            trailingIcon = {
                PreferredIconButton(
                    icon = Icons.AutoMirrored.Filled.Send,
                    isLoading = state.isSending,
                    onClick = {
                        onEvent(EventsDetailChat.AddMessage)
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.padding.medium)
                .align(Alignment.BottomCenter)
        )
    }
    PreferredAnimatedVisibility(visible = state.deleteDialog != null) {
        MessageDeleteDialog(
            state = state,
            onEvent = onEvent
        )
    }
    PreferredAnimatedVisibility(visible = state.clearAllChatDialog) {
        ClearAllChatDialog(
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
private fun UserDetail(
    user: User,
    state: StateDetailChat,
    openScreen: (Screen) -> Unit
) {
    val userStatus = state.userStatus.collectAsState(null).value
    ListItem(
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontSize = MaterialTheme.textSize.medium,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            PreferredAnimatedVisibility(visible = userStatus != null) {
                Text(
                    text = if (userStatus!!.isOnline) "Online"
                    else PrettyTimeUtils.getPrettyTime(userStatus.lastSeen),
                    fontSize = MaterialTheme.textSize.medium,
                )
            }
        },
        leadingContent = {
            PreferredAsyncImage(
                model = user.profileImage,
                contentDescription = "profile image",
                shape = CircleShape,
                modifier = Modifier.size(MaterialTheme.profileSize.medium)
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier.clickable {
            openScreen(Screen.ProfileScreen(userId = user.id))
        }
    )
}

@Composable
private fun MessageDeleteDialog(
    state: StateDetailChat,
    onEvent: (EventsDetailChat) -> Unit
) {
    PreferredWarningDialog(
        title = stringResource(R.string.delete_message),
        description = "really want to delete message",
        isLoading = state.isDeleting,
        onConfirmation = {
            onEvent(
                EventsDetailChat.DeleteMessage
            )
        },
        onDismissRequest = {
            onEvent(
                EventsDetailChat.DismissDeleteDialog
            )
        }
    )
}

@Composable
private fun ClearAllChatDialog(
    state: StateDetailChat,
    onEvent: (EventsDetailChat) -> Unit
) {
    PreferredWarningDialog(
        title = stringResource(R.string.clear_all_chat),
        description = stringResource(R.string.clear_all_chat),
        isLoading = state.isDeleting,
        onConfirmation = {
            onEvent(EventsDetailChat.ClearAllChat)
        },
        onDismissRequest = {
            onEvent(EventsDetailChat.DismissClearAllChatDialog)
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateDetailChat(otherUser = User(firstName = "Hi", lastName = "Pa")),
        onEvent = {},
        popScreen = {},
        openScreen = {}
    )
}

private const val MESSAGE_TIME_SPACING = "     "