package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAnimatedVisibility
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.ui.padding

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
    PreferredColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Compose Notification", style = MaterialTheme.typography.titleLarge)

        PreferredColumn(modifier = Modifier.padding(16.dp)) {
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

        Text("Target Audience", style = MaterialTheme.typography.titleMedium)
        PreferredRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PreferredFilterChip("Student", state.student) {
                onEvent(EventsPushNotification.ChangeStudentFilter)
            }
            PreferredFilterChip("Alumni", state.alumni) {
                onEvent(EventsPushNotification.ChangeAlumniFilter)
            }
            PreferredFilterChip("Staff", state.staff) {
                onEvent(EventsPushNotification.ChangeStaffFilter)
            }
        }

        if (state.student) {
            TargetGroupCard(
                title = "Student Filters",
                mba = state.studentFilter.mba,
                mca = state.studentFilter.mca,
                showBatch = state.isStudentBatch,
                from = state.studentBatch.from,
                to = state.studentBatch.to,
                onMbaClick = { onEvent(EventsPushNotification.ChangeStudentMbaFilter) },
                onMcaClick = { onEvent(EventsPushNotification.ChangeStudentMcaFilter) },
                onBatchClick = { onEvent(EventsPushNotification.ChangeStudentBatchFilter) },
                onFromChange = { onEvent(EventsPushNotification.ChangeStudentBatchFrom(it)) },
                onToChange = { onEvent(EventsPushNotification.ChangeStudentBatchTo(it)) }
            )
        }

        if (state.alumni) {
            TargetGroupCard(
                title = "Alumni Filters",
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

        Spacer(modifier = Modifier.height(24.dp))

        PreferredFilledButton(
            onClick = { onEvent(EventsPushNotification.PushNotification) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Push Notification")
        }
    }
}

@Composable
private fun TargetGroupCard(
    title: String,
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        PreferredColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.padding.small)) {
            Text(title, style = MaterialTheme.typography.titleSmall)

            PreferredRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PreferredFilterChip("MCA", mca, onMcaClick)
                PreferredFilterChip("MBA", mba, onMbaClick)
            }

            PreferredFilterChip("Batch", selected = showBatch, onClick = onBatchClick)

            PreferredAnimatedVisibility(visible = showBatch) {
                PreferredRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PreferredOutlinedTextField(
                        value = from,
                        placeholder = "From",
                        onValueChange = onFromChange,
                        modifier = Modifier.weight(1f)
                    )
                    PreferredOutlinedTextField(
                        value = to,
                        placeholder = "To",
                        onValueChange = onToChange,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StatePushNotification(),
        onEvent = {}
    )
}