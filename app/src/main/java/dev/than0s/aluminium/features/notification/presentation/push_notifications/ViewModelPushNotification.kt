package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.than0s.aluminium.features.notification.domain.data_class.Filters
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.features.notification.domain.use_cases.UseCasePushNotification
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelPushNotification @Inject constructor(
    private val pushNotification: UseCasePushNotification
) : ViewModel() {
    var state by mutableStateOf(StatePushNotification())

    private fun changeTitle(title: String) {
        state = state.copy(
            content = state.content.copy(
                title = title
            )
        )
    }

    private fun changeContent(content: String) {
        state = state.copy(
            content = state.content.copy(
                content = content
            )
        )
    }

    private fun pushNotification() {
        viewModelScope.launch {
            pushNotification(
                PushNotification(
                    content = state.content,
                    filters = Filters(
                        student = state.studentFilter,
                        alumni = state.alumniFilter,
                        staff = state.staff
                    )
                )
            )
        }
    }

    private fun changeStudentFilter() {
        state = state.copy(
            student = !state.student
        )
    }

    private fun changeStudentMcaFilter() {
        state = state.copy(
            studentFilter = state.studentFilter.copy(
                mca = !state.studentFilter.mca
            )
        )
    }

    private fun changeStudentMbaFilter() {
        state = state.copy(
            studentFilter = state.studentFilter.copy(
                mba = !state.studentFilter.mba
            )
        )
    }

    private fun changeStudentBatch() {
        state = state.copy(
            isStudentBatch = !state.isStudentBatch
        )
    }

    private fun changeStudentBatchFrom(year: String) {
        state = state.copy(
            studentBatch = state.studentBatch.copy(
                from = year
            )
        )
    }

    private fun changeStudentBatchTo(year: String) {
        state = state.copy(
            studentBatch = state.studentBatch.copy(
                to = year
            )
        )
    }

    private fun changeAlumniFilter() {
        state = state.copy(
            student = !state.alumni
        )
    }

    private fun changeAlumniMcaFilter() {
        state = state.copy(
            alumniFilter = state.alumniFilter.copy(
                mca = !state.alumniFilter.mca
            )
        )
    }

    private fun changeAlumniMbaFilter() {
        state = state.copy(
            alumniFilter = state.alumniFilter.copy(
                mba = !state.alumniFilter.mba
            )
        )
    }

    private fun changeAlumniBatch() {
        state = state.copy(
            isAlumniBatch = !state.isAlumniBatch
        )
    }

    private fun changeAlumniBatchFrom(year: String) {
        state = state.copy(
            alumniBatch = state.alumniBatch.copy(
                from = year
            )
        )
    }

    private fun changeAlumniBatchTo(year: String) {
        state = state.copy(
            alumniBatch = state.alumniBatch.copy(
                to = year
            )
        )
    }

    private fun changeStaffFilter() {
        state = state.copy(
            staff = !state.staff
        )
    }

    fun onEvent(event: EventsPushNotification) {
        when (event) {
            is EventsPushNotification.ChangeContent -> changeContent(event.content)
            is EventsPushNotification.ChangeTitle -> changeTitle(event.title)
            is EventsPushNotification.PushNotification -> pushNotification()
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
        }
    }
}
