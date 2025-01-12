package dev.than0s.aluminium.features.chat.presentation.screens.group_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.utils.Screen
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
        userMap = viewModel.userMap,
        openScreen = openScreen
    )
}

@Composable
private fun Content(
    state: StateGroupList,
    onEvent: (EventsGroupList) -> Unit,
    userMap: Map<String, User>,
    openScreen: (Screen) -> Unit,
) {
    if (state.isLoading) {

    } else {
        LazyColumn {
            items(state.groupList) { item ->
                val otherUserId = item.usersId[0]
                if (!userMap.containsKey(otherUserId)) {
                    onEvent(EventsGroupList.LoadUser(otherUserId))
                }
                val user = userMap[otherUserId] ?: User()
                GroupItem(
                    user = user,
                    onClick = {
                        openScreen(
                            Screen.ChatDetailScreen(
                                userId = otherUserId,
                                groupId = item.id
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun GroupItem(
    user: User,
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
                text = "call me maybe later...",
                fontSize = MaterialTheme.textSize.medium,
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more option"
            )
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {

}