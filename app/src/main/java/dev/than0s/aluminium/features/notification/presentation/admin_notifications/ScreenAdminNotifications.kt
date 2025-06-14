package dev.than0s.aluminium.features.notification.presentation.admin_notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredCard
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFloatingActionButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredNoData
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerListItem
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.features.notification.domain.data_class.NotificationContent
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize

@Composable
fun ScreenAdminNotifications(
    openScreen: (Screen) -> Unit,
    viewModel: ViewModelAdminNotifications = hiltViewModel()
) {
    Content(
        state = viewModel.state,
        openScreen = openScreen,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: StateAdminNotifications,
    openScreen: (Screen) -> Unit,
    onEvent: (EventsAdminNotifications) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {
            onEvent(EventsAdminNotifications.GetNotifications)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            ShimmerLoading()
        } else if (state.notificationList.isEmpty()) {
            PreferredNoData(
                title = stringResource(R.string.no_notifications),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                contentPadding = PaddingValues(MaterialTheme.padding.small)
            ) {
                items(state.notificationList) { noty ->
                    var sheet by remember { mutableStateOf(false) }

                    PreferredCard {
                        ListItem(
                            leadingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_launcher_foreground),
                                    contentDescription = null,
                                    modifier = Modifier.size(MaterialTheme.profileSize.medium)
                                )
                            },
                            headlineContent = {
                                Text(
                                    text = noty.content.title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = noty.content.content,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            trailingContent = {
                                Text(
                                    text = if (noty.pushStatus) "Completed" else "Pending",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier.clickable(onClick = {
                                sheet = !sheet
                            })
                        )
                    }

                    if (sheet) {
                        BottomSheet(
                            noty = noty,
                            onDismissRequest = {
                                sheet = false
                            },
                        )
                    }
                }
            }
        }
        PreferredFloatingActionButton(
            onClick = {
                openScreen(Screen.PushNotificationScreen)
            },
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.padding.medium)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
    noty: PushNotification,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = if (noty.pushStatus) "Completed" else "Pending",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = noty.content.title,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = noty.content.content,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = PrettyTimeUtils.getPrettyTime(noty.timestamp)
        )
    }
}

@Composable
private fun ShimmerLoading() {
    PreferredColumn(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        for (i in 1..10) {
            ShimmerNoty()
        }
    }
}

@Composable
private fun ShimmerNoty() {
    PreferredCard {
        ShimmerListItem()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateAdminNotifications(
            notificationList = listOf(
                PushNotification(
                    content = NotificationContent(title = "hi", content = "bye")
                )
            )
        ),
        openScreen = {},
        onEvent = {}
    )
}