package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarAction
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.domain.data_class.AlumniFilter
import dev.than0s.aluminium.features.notification.domain.data_class.BatchFilter
import dev.than0s.aluminium.features.notification.domain.data_class.StudentFilter
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCasePushNotification
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelPushNotification @Inject constructor(
    private val pushNotification: UseCasePushNotification
) : ViewModel() {
    var state by mutableStateOf(StatePushNotification())

    private fun changeTitle(title: String) {
        state = state.copy(
            notification = state.notification.copy(
                content = state.notification.content.copy(
                    title = title
                )
            )
        )
    }

    private fun changeContent(content: String) {
        state = state.copy(
            notification = state.notification.copy(
                content = state.notification.content.copy(
                    content = content
                )
            )
        )
    }

    private fun pushNotification(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = pushNotification(state.notification)) {
                is Resource.Error -> {
                    SnackbarEvent(
                        message = result.uiText ?: UiText.unknownError(),
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.try_again),
                            action = {
                                pushNotification(onSuccess)
                            }
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarEvent(
                        message = UiText.DynamicString("Notification push successfully")
                    )
                    onSuccess()
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun changeStudentFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = if (state.notification.filters.student == null) StudentFilter() else null
                )
            )
        )
    }

    private fun changeStudentMcaFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = state.notification.filters.student!!.copy(
                        mca = !state.notification.filters.student!!.mca
                    )
                )
            )
        )
    }

    private fun changeStudentMbaFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = state.notification.filters.student!!.copy(
                        mba = !state.notification.filters.student!!.mba
                    )
                )
            )
        )
    }

    private fun changeStudentBatch() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = state.notification.filters.student!!.copy(
                        batch = if (state.notification.filters.student!!.batch == null) BatchFilter() else null
                    )
                )
            )
        )
    }

    private fun changeStudentBatchFrom(year: String) {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = state.notification.filters.student!!.copy(
                        batch = state.notification.filters.student!!.batch!!.copy(
                            from = year
                        )
                    )
                )
            )
        )
    }

    private fun changeStudentBatchTo(year: String) {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    student = state.notification.filters.student!!.copy(
                        batch = state.notification.filters.student!!.batch!!.copy(
                            to = year
                        )
                    )
                )
            )
        )
    }

    private fun changeAlumniFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = if (state.notification.filters.student == null) AlumniFilter() else null
                )
            )
        )
    }

    private fun changeAlumniMcaFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = state.notification.filters.alumni!!.copy(
                        mca = !state.notification.filters.alumni!!.mca
                    )
                )
            )
        )
    }

    private fun changeAlumniMbaFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = state.notification.filters.alumni!!.copy(
                        mba = !state.notification.filters.alumni!!.mba
                    )
                )
            )
        )
    }

    private fun changeAlumniBatch() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = state.notification.filters.alumni!!.copy(
                        batch = if (state.notification.filters.alumni!!.batch == null) BatchFilter() else null
                    )
                )
            )
        )
    }

    private fun changeAlumniBatchFrom(year: String) {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = state.notification.filters.alumni!!.copy(
                        batch = state.notification.filters.alumni!!.batch!!.copy(
                            from = year
                        )
                    )
                )
            )
        )
    }

    private fun changeAlumniBatchTo(year: String) {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    alumni = state.notification.filters.alumni!!.copy(
                        batch = state.notification.filters.alumni!!.batch!!.copy(
                            to = year
                        )
                    )
                )
            )
        )
    }

    private fun changeStaffFilter() {
        state = state.copy(
            notification = state.notification.copy(
                filters = state.notification.filters.copy(
                    staff = !state.notification.filters.staff
                )
            )
        )
    }

    private fun increasePageIndex() {
        state = state.copy(
            pageIndex = state.pageIndex + 1
        )
    }

    private fun decreasePageIndex() {
        state = state.copy(
            pageIndex = state.pageIndex - 1
        )
    }

    fun onEvent(event: EventsPushNotification) {
        when (event) {
            is EventsPushNotification.ChangeContent -> changeContent(event.content)
            is EventsPushNotification.ChangeTitle -> changeTitle(event.title)
            is EventsPushNotification.PushNotification -> pushNotification(event.onSuccess)
            is EventsPushNotification.ChangeStudentFilter -> changeStudentFilter()
            is EventsPushNotification.ChangeStudentMcaFilter -> changeStudentMcaFilter()
            is EventsPushNotification.ChangeStudentMbaFilter -> changeStudentMbaFilter()
            is EventsPushNotification.ChangeStudentBatchFrom -> changeStudentBatchFrom(event.year)
            is EventsPushNotification.ChangeStudentBatchTo -> changeStudentBatchTo(event.year)
            is EventsPushNotification.ChangeAlumniFilter -> changeAlumniFilter()
            is EventsPushNotification.ChangeAlumniMcaFilter -> changeAlumniMcaFilter()
            is EventsPushNotification.ChangeAlumniMbaFilter -> changeAlumniMbaFilter()
            is EventsPushNotification.ChangeAlumniBatchFrom -> changeAlumniBatchFrom(event.year)
            is EventsPushNotification.ChangeAlumniBatchTo -> changeAlumniBatchTo(event.year)
            is EventsPushNotification.ChangeStaffFilter -> changeStaffFilter()
            is EventsPushNotification.ChangeAlumniBatchFilter -> changeAlumniBatch()
            is EventsPushNotification.ChangeStudentBatchFilter -> changeStudentBatch()
            is EventsPushNotification.OnNextClick -> increasePageIndex()
            is EventsPushNotification.OnPreviousClick -> decreasePageIndex()
        }
    }
}
