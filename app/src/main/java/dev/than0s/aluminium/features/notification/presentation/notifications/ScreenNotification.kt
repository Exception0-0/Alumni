package dev.than0s.aluminium.features.notification.presentation.notifications

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextFieldDropDown

@Composable
fun ScreenPushNotification(
    viewModel: ViewModelNotification = hiltViewModel()
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun Content(
    state: StateNotification,
    onEvent: (EventsNotification) -> Unit,
) {

}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateNotification(),
        onEvent = {}
    )
}