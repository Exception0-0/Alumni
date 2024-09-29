package dev.than0s.aluminium.features.chat.presentation.screen.chat_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import dev.than0s.aluminium.R
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.ChatListItem
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun ChatListScreen(viewModel: ChatListViewModel = hiltViewModel()) {

}

@Composable
private fun ChatListContent(
    userList: List<ChatListItem>
) {
    LazyColumn {
        items(userList) {
            ChatListCard(
                chatListItem = it
            )
        }
    }
}

@Composable
private fun ChatListCard(
    chatListItem: ChatListItem
) {
    ElevatedCard(
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                AsyncImage(
                    model = chatListItem.userProfileImage,
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
                        text = "${chatListItem.userFirstName} ${chatListItem.userLastName}"
                    )
                    Text(
                        text = chatListItem.lastChat.message
                    )
                }
            }
            TimeShower(
                timestamp = chatListItem.lastChat.timeStamp
            )
        }
    }
}

@Composable
fun TimeShower(timestamp: Timestamp) {
    Text(
        text = "06:00 PM",
        fontSize = MaterialTheme.textSize.extraSmall,
        fontWeight = FontWeight.W200
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ChatListPreview() {
    ChatListContent(
        listOf(
            ChatListItem(
                userFirstName = "Than0s",
                userLastName = "Op",
                lastChat = Chat(
                    message = "Hello I'm Than0s. Nothing more than that"
                )
            )
        )
    )
}