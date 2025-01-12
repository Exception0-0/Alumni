package dev.than0s.aluminium.features.chat.presentation.screens.detail_chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredIconButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Content(
    state: StateDetailChat,
    onEvent: (EventsDetailChat) -> Unit,
    popScreen: () -> Unit,
) {
    val chatList = state.chatFlow.collectAsState(emptyList()).value
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
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
            )
        },
        bottomBar = {
            PreferredTextField(
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
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(chatList) { item ->
                    Text(
                        text = item.message
                    )
                }
            }
        }
    )
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