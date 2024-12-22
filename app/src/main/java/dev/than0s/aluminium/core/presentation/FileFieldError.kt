package dev.than0s.aluminium.core.presentation

import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.data.remote.error.Error

sealed class FileFieldError(message: UiText) : Error(message = message) {
    data object FileEmpty : FileFieldError(UiText.StringResource(R.string.file_not_picked_error))
}