package dev.than0s.aluminium.features.chat.presentation.screen.chat_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import dev.than0s.aluminium.ui.spacing

@Composable
fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    val receiverChatList = viewModel.receiverChatFlow.collectAsState(emptyList()).value
    val senderChatList = viewModel.senderChatFlow.collectAsState(emptyList()).value
    val chatList = (receiverChatList + senderChatList).sortedBy { it.timeStamp }
    ChatDetailContent(
        chatList = chatList,
        user = viewModel.user,
        currentChat = viewModel.currentChat,
        onSendClick = viewModel::onSendClick,
        onChatRemoveClick = viewModel::onChatRemoveClick,
        onChatMessageChange = viewModel::onChatMessageChange,
    )
}

@Composable
private fun ChatDetailContent(
    chatList: List<Chat>,
    user: User,
    currentChat: Chat,
    onSendClick: (() -> Unit) -> Unit,
    onChatRemoveClick: (Chat, () -> Unit) -> Unit,
    onChatMessageChange: (String) -> Unit
) {
    var circularProgressIndicatorState by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Surface(

                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    AsyncImage(
                        model = user.profileImage,
                        placeholder = painterResource(
                            id = R.drawable.ic_launcher_background
                        ),
                        contentDescription = "user profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        Text(
                            text = "${user.firstName} ${user.lastName}"
                        )
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AluminiumTextField(
                    value = currentChat.message,
                    placeholder = "Message",
                    onValueChange = onChatMessageChange,
                    trailingIcon = {
                        AluminiumLoadingIconButton(
                            icon = Icons.AutoMirrored.Rounded.Send,
                            circularProgressIndicatorState = circularProgressIndicatorState,
                            onClick = {
                                circularProgressIndicatorState = true
                                onSendClick {
                                    circularProgressIndicatorState = false
                                }
                            },
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    ) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            items(chatList) { chat ->
                ChatItem(
                    chat = chat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(
                            align = if (chat.senderId == currentUserId) Alignment.End else Alignment.Start
                        )
                )
            }
        }
    }
}

fun getTime(timestamp: Timestamp): String {
    return "${timestamp.toDate().hours}:${timestamp.toDate().minutes}"
}

@Composable
private fun ChatItem(
    chat: Chat,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(256.dp)
    ) {
        Text(
            text = chat.message,
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End)
        ) {
            Text(
                text = getTime(chat.timeStamp),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatDetailPreview() {
    ChatDetailContent(
        listOf(
            Chat(
                message = "Hi, I want to say something to you sorry for every thing",
                senderId = "1"
            ),
            Chat(
                message = "Hi, I want to say something to you sorry for every thing"
            )
        ),
        User(
            firstName = "Than0s",
            lastName = "Op"
        ),
        Chat(),
        {}, { _, _ -> }, {}
    )
}