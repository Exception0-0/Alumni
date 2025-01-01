package dev.than0s.aluminium.features.profile.presentation.screens.profile

sealed class ProfileEvents {
    data class OnTabChanged(val tabIndex: Int) : ProfileEvents()
    data object OnLoadProfile : ProfileEvents()
}