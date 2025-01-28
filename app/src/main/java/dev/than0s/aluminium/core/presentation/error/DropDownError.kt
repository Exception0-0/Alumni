package dev.than0s.aluminium.core.presentation.error

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.domain.error.PreferredError
import dev.than0s.aluminium.core.presentation.utils.UiText

sealed class DropDownError(message: UiText) : PreferredError(message) {
    data object EmptyField : DropDownError(UiText.StringResource(R.string.drop_down_empty_error))
}