package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.registration.domain.use_cases.SubmitRegistrationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: SubmitRegistrationUseCase
) :
    ViewModel() {
    var screenState by mutableStateOf(RegistrationState())


    private fun onEmailChange(email: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                email = email
            )
        )
    }

    private fun onRoleChange(category: Role) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                role = category
            )
        )
    }

    private fun onCollegeIdChange(rollNo: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                collegeId = rollNo
            )
        )
    }

    private fun onFirstNameChange(firstName: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                firstName = firstName
            )
        )
    }

    private fun onMiddleNameChange(middleName: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                middleName = middleName
            )
        )
    }

    private fun onLastNameChange(lastName: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                lastName = lastName
            )
        )
    }

    private fun onBatchFromChange(from: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                batchFrom = from
            )
        )
    }

    private fun onBatchToChange(to: String) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                batchTo = to
            )
        )
    }

    private fun onCollegeIdCardChange(uri: Uri?) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                idCardImage = uri
            )
        )
    }

    private fun onCourseChange(course: Course) {
        screenState = screenState.copy(
            registrationForm = screenState.registrationForm.copy(
                course = course
            )
        )
    }

    private fun onNextClick() {
        screenState = screenState.copy(
            formIndex = screenState.formIndex + 1
        )
    }

    private fun onPreviousClick() {
        screenState = screenState.copy(
            formIndex = screenState.formIndex - 1
        )
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)

            val registrationResult = registerUseCase(screenState.registrationForm)

            screenState = screenState.copy(
                emailError = registrationResult.emailError,
                batchFromError = registrationResult.batchFromError,
                batchToError = registrationResult.batchToError,
                firstNameError = registrationResult.firstNameError,
                lastNameError = registrationResult.lastNameError,
                collageIdError = registrationResult.rollNoError,
                middleNameError = registrationResult.middleNameError,
            )

            when (registrationResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = registrationResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_register)
                        )
                    )
                }

                null -> {}
            }

            screenState = screenState.copy(isLoading = false)
        }
    }

    fun onEvent(event: RegistrationEvents) {
        when (event) {
            is RegistrationEvents.OnEmailChange -> onEmailChange(event.email)
            is RegistrationEvents.OnRoleChange -> onRoleChange(event.role)
            is RegistrationEvents.OnCollegeIdChange -> onCollegeIdChange(event.rollNo)
            is RegistrationEvents.OnFirstNameChange -> onFirstNameChange(event.firstName)
            is RegistrationEvents.OnMiddleNameChange -> onMiddleNameChange(event.middleName)
            is RegistrationEvents.OnLastNameChange -> onLastNameChange(event.lastName)
            is RegistrationEvents.OnBatchFromChange -> onBatchFromChange(event.from)
            is RegistrationEvents.OnBatchToChange -> onBatchToChange(event.to)
            is RegistrationEvents.OnCourseChange -> onCourseChange(event.course)
            is RegistrationEvents.OnCollegeIdCardChange -> onCollegeIdCardChange(event.idCard)
            is RegistrationEvents.OnRegisterClick -> onRegisterClick()
            is RegistrationEvents.OnPreviousClick -> onPreviousClick()
            is RegistrationEvents.OnNextClick -> onNextClick()
        }
    }
}