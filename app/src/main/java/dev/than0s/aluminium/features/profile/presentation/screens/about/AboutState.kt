package dev.than0s.aluminium.features.profile.presentation.screens.about

import dev.than0s.aluminium.core.domain.data_class.AboutInfo

data class AboutState(
    val aboutInfo: AboutInfo = AboutInfo(),
    val isLoading: Boolean = false
)
