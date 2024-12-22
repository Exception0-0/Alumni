package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role

sealed class RegistrationEvents {
    data class OnEmailChange(val email: String) : RegistrationEvents()
    data class OnRoleChange(val role: Role) : RegistrationEvents()
    data class OnCollegeIdChange(val rollNo: String) : RegistrationEvents()
    data class OnFirstNameChange(val firstName: String) : RegistrationEvents()
    data class OnMiddleNameChange(val middleName: String) : RegistrationEvents()
    data class OnLastNameChange(val lastName: String) : RegistrationEvents()
    data class OnBatchFromChange(val from: String) : RegistrationEvents()
    data class OnBatchToChange(val to: String) : RegistrationEvents()
    data class OnCourseChange(val course: Course) : RegistrationEvents()
    data class OnCollegeIdCardChange(val idCard: Uri?) : RegistrationEvents()
    data object OnRegisterClick : RegistrationEvents()

}