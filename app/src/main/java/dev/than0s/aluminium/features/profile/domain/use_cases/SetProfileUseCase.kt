package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.features.profile.domain.data_class.SetProfileResult
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.error.FileFieldError
import dev.than0s.aluminium.core.presentation.error.TextFieldError
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SetProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(param: User): SetProfileResult {
        val firstNameError = param.firstName.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }

        val lastNameError = param.lastName.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }

        val bioError = param.bio.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else null
        }

        val profileImageError = param.profileImage.let {
            if (it == null) FileFieldError.FileEmpty
            else null
        }

        val coverImageError = param.coverImage.let {
            if (it == null) FileFieldError.FileEmpty
            else null
        }

        if (firstNameError != null || lastNameError != null || bioError != null || profileImageError != null || coverImageError != null) {
            return SetProfileResult(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                bioError = bioError,
                profileImageError = profileImageError,
            )
        }

        return SetProfileResult(
            result = repository.setUserProfile(param)
        )
    }
}