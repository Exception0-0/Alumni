package dev.than0s.aluminium.features.notification.presentation.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredNoData
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerListItem
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize

@Composable
fun ScreenNotification(
    viewModel: ViewModelNotification = hiltViewModel()
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: StateNotification,
    onEvent: (EventsNotification) -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {
            onEvent(EventsNotification.GetNotifications)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            ShimmerLoading()
        } else if (state.notifications.isEmpty()) {
            PreferredNoData(
                title = stringResource(R.string.no_notifications),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                contentPadding = PaddingValues(MaterialTheme.padding.small)
            ) {
                items(state.notifications) { noty ->
                    var dialog by remember { mutableStateOf(false) }
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
                                    text = noty.title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = noty.content,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            trailingContent = {
                                Text(
                                    text = PrettyTimeUtils.getPrettyTime(noty.timestamp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier.clickable(onClick = {
                                dialog = !dialog
                            })
                        )
                    }
                    if (dialog) {
                        DeleteDialog(
                            isLoading = state.isLoading,
                            onDismissRequest = { dialog = false },
                            onConfirmation = { onEvent(EventsNotification.RemoteNotification(noty)) }
                        )
                    }
                }
            }
        }
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

@Composable
private fun DeleteDialog(
    isLoading: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    PreferredWarningDialog(
        title = stringResource(R.string.delete_notification),
        description = stringResource(R.string.delete_warning),
        isLoading = isLoading,
        onDismissRequest = onDismissRequest,
        onConfirmation = onConfirmation
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateNotification(
            notifications = listOf(
                CloudNotification(
                    title = "Hi",
                    content = "every one"
                ),
                CloudNotification(
                    title = "Hi",
                    content = "every one"
                ),
                CloudNotification(
                    title = "Hi",
                    content = "every one"
                ),
            )
        ),
        onEvent = {}
    )
}