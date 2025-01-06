package dev.than0s.aluminium.features.registration.presentation.screens.requests

import android.net.Uri
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

data class RequestScreenState(
    val requestsList: List<RegistrationForm> = emptyList(),
    val requestSelection: Pair<String, Boolean>? = null,
    val selectedIdCard: Uri? = null,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
)