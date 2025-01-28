package dev.than0s.aluminium.core.presentation.error

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.domain.error.PreferredError

sealed class TextFieldError(message: UiText) : PreferredError(message) {
    data object FieldEmpty : TextFieldError(UiText.StringResource(R.string.field_empty_error))
    data object InvalidEmail : TextFieldError(UiText.StringResource(R.string.invalid_email))
    data object InvalidPassword : TextFieldError(UiText.StringResource(R.string.invalid_password))
    data object InvalidMobile : TextFieldError(UiText.StringResource(R.string.invalid_mobile_number))
    data object InvalidLink : TextFieldError(UiText.StringResource(R.string.invalid_link_format))
    data object ShortCollegeID : TextFieldError(UiText.StringResource(R.string.short_college_id))
    data object ShortYear : TextFieldError(UiText.StringResource(R.string.short_year))
}