package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.domain.util.generateUniqueId
import dev.than0s.aluminium.core.domain.util.isValidEmail
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.data_class.SubmitRegistrationResult
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class AddRegistrationRequestUseCase @Inject constructor(private val repository: RegistrationRepository) {
    suspend operator fun invoke(form: RegistrationForm): SubmitRegistrationResult {
        val emailError = form.email.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else if (!isValidEmail(it)) TextFieldError.InvalidEmail
            else null
        }
        val firstNameError = form.firstName.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val lastNameError = form.lastName.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val middleNameError = form.middleName.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val batchFromError = form.batchFrom?.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val batchToError = form.batchTo?.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }
        val rollNoError = form.collegeId.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }

        if (emailError != null ||
            firstNameError != null ||
            lastNameError != null ||
            middleNameError != null ||
            batchToError != null ||
            batchFromError != null ||
            rollNoError != null
        ) {
            return SubmitRegistrationResult(
                emailError = emailError,
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                middleNameError = middleNameError,
                batchToError = batchToError,
                batchFromError = batchFromError,
                rollNoError = rollNoError
            )
        }

        return SubmitRegistrationResult(
            result = repository.addRegistrationRequest(
                form.copy(
                    id = generateUniqueId(),
                    timestamp = System.currentTimeMillis()
                )
            )
        )
    }
}