package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.util.TextFieldLimits
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAnimatedVisibility
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField

@Composable
fun ScreenPushNotification(
    viewModel: ViewModelPushNotification = hiltViewModel()
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun Content(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit,
) {
    val pages = getPages(state)
    val isLastIndex = state.pageIndex == pages.lastIndex
    val isIndexZero = state.pageIndex == 0

    Box(modifier = Modifier.fillMaxSize()) {
        PreferredColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
        ) {
            Text(
                text = pages[state.pageIndex].name,
                style = MaterialTheme.typography.titleLarge
            )
            pages[state.pageIndex].content(state, onEvent)

            PreferredRow {
                IconButton(
                    onClick = {
                        onEvent(EventsPushNotification.OnPreviousClick)
                    },
                    enabled = !isIndexZero && !state.isLoading,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "previous page"
                        )
                    }
                )

                IconButton(
                    onClick = {
                        onEvent(EventsPushNotification.OnNextClick)
                    },
                    enabled = !isLastIndex && !state.isLoading,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "next page"
                        )
                    }
                )
            }

            PreferredAnimatedVisibility(
                visible = isLastIndex
            ) {
                PreferredFilledButton(
                    onClick = { onEvent(EventsPushNotification.PushNotification) },
                    modifier = Modifier.align(Alignment.End),
                    isLoading = state.isLoading,
                    content = {
                        Text("Push Notification")
                    }
                )
            }
        }
    }
}

@Composable
private fun TargetGroupCard(
    mba: Boolean,
    mca: Boolean,
    showBatch: Boolean,
    from: String,
    to: String,
    onMbaClick: () -> Unit,
    onMcaClick: () -> Unit,
    onBatchClick: () -> Unit,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit
) {
    PreferredColumn {
        PreferredRow {
            PreferredFilterChip(
                label = "MCA",
                selected = mca,
                onClick = onMcaClick
            )

            PreferredFilterChip(
                label = "MBA",
                selected = mba,
                onClick = onMbaClick,
            )
        }

        PreferredFilterChip(
            label = "Batch",
            selected = showBatch,
            onClick = onBatchClick
        )

        PreferredAnimatedVisibility(
            visible = showBatch
        ) {
            PreferredRow(
                modifier = Modifier.width(TextFieldDefaults.MinWidth)
            ) {
                PreferredTextField(
                    value = from,
                    placeholder = "From",
                    onValueChange = onFromChange,
                    keyboardType = KeyboardType.Number,
                    maxChar = TextFieldLimits.MAX_BATCH,
                    modifier = Modifier.weight(0.5f),
                )
                PreferredTextField(
                    value = to,
                    placeholder = "To",
                    onValueChange = onToChange,
                    keyboardType = KeyboardType.Number,
                    maxChar = TextFieldLimits.MAX_BATCH,
                    modifier = Modifier.weight(0.5f),
                )
            }
        }
    }
}

@Composable
private fun ComposeNotification(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit
) {
    PreferredColumn {
        PreferredTextField(
            value = state.content.title,
            placeholder = "Title",
            onValueChange = {
                onEvent(EventsPushNotification.ChangeTitle(it))
            },
            label = "Notification Title"
        )
        PreferredTextField(
            value = state.content.content,
            placeholder = "Message Content",
            onValueChange = {
                onEvent(EventsPushNotification.ChangeContent(it))
            },
            label = "Content"
        )
    }
}

@Composable
private fun TargetAudience(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit
) {
    PreferredRow {
        PreferredFilterChip(
            label = "Student",
            selected = state.student,
            onClick = {
                onEvent(EventsPushNotification.ChangeStudentFilter)
            }
        )
        PreferredFilterChip(
            label = "Alumni",
            selected = state.alumni,
            onClick = {
                onEvent(EventsPushNotification.ChangeAlumniFilter)
            }
        )
        PreferredFilterChip(
            label = "Staff",
            selected = state.staff,
            onClick = {
                onEvent(EventsPushNotification.ChangeStaffFilter)
            }
        )
    }
}

@Composable
private fun StudentFilter(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit
) {
    TargetGroupCard(
        mba = state.studentFilter.mba,
        mca = state.studentFilter.mca,
        showBatch = state.isStudentBatch,
        from = state.studentBatch.from,
        to = state.studentBatch.to,
        onMbaClick = { onEvent(EventsPushNotification.ChangeStudentMbaFilter) },
        onMcaClick = { onEvent(EventsPushNotification.ChangeStudentMcaFilter) },
        onBatchClick = { onEvent(EventsPushNotification.ChangeStudentBatchFilter) },
        onFromChange = { onEvent(EventsPushNotification.ChangeStudentBatchFrom(it)) },
        onToChange = { onEvent(EventsPushNotification.ChangeStudentBatchTo(it)) },
    )
}

@Composable
private fun AlumniFilter(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit
) {
    TargetGroupCard(
        mba = state.alumniFilter.mba,
        mca = state.alumniFilter.mca,
        showBatch = state.isAlumniBatch,
        from = state.alumniBatch.from,
        to = state.alumniBatch.to,
        onMbaClick = { onEvent(EventsPushNotification.ChangeAlumniMbaFilter) },
        onMcaClick = { onEvent(EventsPushNotification.ChangeAlumniMcaFilter) },
        onBatchClick = { onEvent(EventsPushNotification.ChangeAlumniBatchFilter) },
        onFromChange = { onEvent(EventsPushNotification.ChangeAlumniBatchFrom(it)) },
        onToChange = { onEvent(EventsPushNotification.ChangeAlumniBatchTo(it)) }
    )
}

private data class ComposeNotificationPage(
    val name: String,
    val content: @Composable ((StatePushNotification, (EventsPushNotification) -> Unit) -> Unit)
)

private fun getPages(
    screenState: StatePushNotification,
): List<ComposeNotificationPage> {
    val pages = mutableListOf<ComposeNotificationPage>()
    pages.add(
        ComposeNotificationPage(
            name = "Compose Notification",
            content = { state, onEvent ->
                ComposeNotification(
                    state = state,
                    onEvent = onEvent
                )
            }
        )
    )
    pages.add(
        ComposeNotificationPage(
            name = "Target Audience",
            content = { state, onEvent ->
                TargetAudience(
                    state = state,
                    onEvent = onEvent
                )
            }
        )
    )
    if (screenState.alumni) {
        pages.add(
            ComposeNotificationPage(
                name = "Alumni Filters",
                content = { state, onEvent ->
                    AlumniFilter(
                        state = state,
                        onEvent = onEvent
                    )
                }
            )
        )
    }
    if (screenState.student) {
        pages.add(
            ComposeNotificationPage(
                name = "Student Filters",
                content = { state, onEvent ->
                    StudentFilter(
                        state = state,
                        onEvent = onEvent
                    )
                }
            )
        )
    }
    return pages
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StatePushNotification(),
        onEvent = {}
    )
}