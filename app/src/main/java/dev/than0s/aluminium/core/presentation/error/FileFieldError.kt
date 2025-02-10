package dev.than0s.aluminium.core.presentation.error

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.domain.error.PreferredError

sealed class FileFieldError(message: UiText) : PreferredError(message) {
    data object FileEmpty : FileFieldError(UiText.StringResource(R.string.file_not_picked_error))
}