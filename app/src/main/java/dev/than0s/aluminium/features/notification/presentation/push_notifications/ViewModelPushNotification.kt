package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.features.notification.domain.data_class.AlumniFilter
import dev.than0s.aluminium.features.notification.domain.data_class.Filters
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
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
            var student: StudentFilter? = null
            var alumni: AlumniFilter? = null
            if (state.student) {
                student = if (state.isStudentBatch) {
                    state.studentFilter.copy(
                        batch = state.studentBatch
                    )
                } else {
                    state.studentFilter
                }
            }
            if (state.alumni) {
                alumni = if (state.isAlumniBatch) {
                    state.alumniFilter.copy(
                        batch = state.alumniBatch
                    )
                } else {
                    state.alumniFilter
                }
            }
            pushNotification(
                PushNotification(
                    content = state.content,
                    filters = Filters(
                        student = student,
                        alumni = alumni,
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
            alumni = !state.alumni
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
