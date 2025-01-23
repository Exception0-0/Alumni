package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredIconButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
import dev.than0s.aluminium.ui.textSize

@Composable
fun ScreenDetailChat(
    viewModel: ViewModelDetailChat = hiltViewModel(),
    popScreen: () -> Unit,
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        popScreen = popScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: StateDetailChat,
    onEvent: (EventsDetailChat) -> Unit,
    popScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    UserDetail(
                        user = state.otherUser
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
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "more option"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        },
        bottomBar = {
            PreferredOutlinedTextField(
                placeholder = "message",
                value = state.chatMessage,
                onValueChange = {
                    onEvent(EventsDetailChat.OnMessageChange(it))
                },
                trailingIcon = {
                    PreferredIconButton(
                        icon = Icons.AutoMirrored.Filled.Send,
                        isLoading = state.isSending,
                        onClick = {
                            onEvent(EventsDetailChat.AddMessage)
                        }
                    )
                },
                leadingIcon = {
                    PreferredIconButton(
                        onClick = {
                        },
                        icon = Icons.Outlined.AddPhotoAlternate,

                        )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.padding.medium)
            )
        },
    ) {
        val chatList = state.chatFlow.collectAsState(emptyList()).value
        LazyColumn(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                            ),
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

@Composable
private fun UserDetail(
    user: User
) {
    ListItem(
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontSize = MaterialTheme.textSize.medium,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(
                text = "Online",
                fontSize = MaterialTheme.textSize.medium,
            )
        },
        leadingContent = {
            PreferredAsyncImage(
                model = user.profileImage,
                contentDescription = "profile image",
                shape = CircleShape,
                modifier = Modifier.size(MaterialTheme.profileSize.medium)
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateDetailChat(otherUser = User(firstName = "Hi", lastName = "Pa")),
        onEvent = {},
        popScreen = {}
    )
}

private const val MESSAGE_TIME_SPACING = "     "