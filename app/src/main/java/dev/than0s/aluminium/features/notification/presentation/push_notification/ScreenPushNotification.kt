package dev.than0s.aluminium.features.notification.presentation.push_notification

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextFieldDropDown

@Composable
fun ScreenPushNotification() {

}

@Composable
private fun Content(
    state: StateScreenPushNotification,
    onEvent: (EventsPushNotification) -> Unit,
) {
    PreferredColumn {
        FilterRow(
            state = state,
            onEvent = onEvent
        )

        PreferredTextField(
            value = state.title,
            placeholder = "Title",
            onValueChange = {
            },
            modifier = Modifier.fillMaxWidth()
        )
        PreferredTextField(
            value = state.content,
            placeholder = "Content",
            onValueChange = {

            },
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )
        PreferredFilledButton(
            onClick = {

            },
            content = {
                Text(text = "Push")
            }
        )
    }
}

@Composable
private fun FilterRow(
    state: StateScreenPushNotification,
    onEvent: (EventsPushNotification) -> Unit,
) {
    PreferredColumn {
        PreferredRow {
            PreferredFilterChip(
                label = Role.Alumni.name,
                selected = state.pushNotification.categories.alumni,
                onClick = {
                }
            )
            PreferredFilterChip(
                label = Course.MCA.name,
                selected = state.pushNotification.categories.mca,
                onClick = {

                }
            )
            PreferredFilterChip(
                label = Course.MBA.name,
                selected = state.pushNotification.categories.mba,
                onClick = {

                }
            )
            PreferredFilterChip(
                label = Role.Staff.name,
                selected = state.pushNotification.categories.staff,
                onClick = {

                }
            )
        }
        PreferredRow {
            PreferredTextFieldDropDown(
                value = state.pushNotification.batch?.from ?: "",
                onValueChange = {
                },
                placeholder = "From",
                expanded = state.fromExpanded,
                dropList = listOf("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"),
                onStateChanged = {

                },
                enabled = !state.isLoading,
                modifier = Modifier.weight(0.3f)
            )
            PreferredTextFieldDropDown(
                value = state.pushNotification.batch?.to ?: "",
                onValueChange = {
//                    onEvent(RegistrationEvents.OnCourseChange(Course.valueOf(it)))
                },
                placeholder = "To",
                expanded = state.toExpanded,
                dropList = Course.entries.map { it.name },
                onStateChanged = {
//                    onEvent(RegistrationEvents.ChangeCourseDropState(it))
                },
                enabled = !state.isLoading,
                modifier = Modifier.weight(0.3f)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StateScreenPushNotification(),
        onEvent = {}
    )
}