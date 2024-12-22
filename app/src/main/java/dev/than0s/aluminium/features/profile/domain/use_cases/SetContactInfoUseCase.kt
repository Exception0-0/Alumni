package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.domain.util.isValidEmail
import dev.than0s.aluminium.core.domain.util.isValidLink
import dev.than0s.aluminium.core.domain.util.isValidPhoneNumber
import dev.than0s.aluminium.core.presentation.TextFieldError
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.SetContactInfoResult
import dev.than0s.aluminium.features.profile.domain.repository.ContactRepository
import javax.inject.Inject

class SetContactInfoUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend operator fun invoke(param: ContactInfo): SetContactInfoResult {
        val emailError = param.email?.let {
            if (it.isBlank()) null
            else if (!isValidEmail(it)) TextFieldError.InvalidEmail
            else null
        }

        val mobileError = param.mobile?.let {
            if (it.isBlank()) null
            else if (!isValidPhoneNumber(it)) TextFieldError.InvalidMobile
            else null
        }

        val socialHandelError = param.socialHandles?.let {
            if (it.isBlank()) null
            else if (!isValidLink(it)) TextFieldError.InvalidLink
            else null
        }

        if (emailError != null || mobileError != null || socialHandelError != null) {
            return SetContactInfoResult(
                emailError = emailError,
                mobileError = mobileError,
                socialHandel = socialHandelError
            )
        }

        return SetContactInfoResult(
            result = repository.setContactInfo(param)
        )
    }
}