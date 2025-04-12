package dev.than0s.aluminium.core.domain.error

import dev.than0s.aluminium.core.presentation.utils.UiText

abstract class PreferredError(val message: UiText? = null)

class SimpleError(message: UiText) : PreferredError(message)