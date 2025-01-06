package dev.than0s.aluminium.features.registration.presentation.screens.requests

import android.net.Uri

sealed class RequestScreenEvents {
    data class OnAcceptClick(val requestId: String) : RequestScreenEvents()
    data class OnRejectClick(val requestId: String) : RequestScreenEvents()
    data class ShowWarningDialog(val formId: String, val accepted: Boolean) : RequestScreenEvents()
    data class ShowIdCard(val imageUri: Uri) : RequestScreenEvents()
    data class OnSearchTextChanged(val text: String) : RequestScreenEvents()
    data object OnAllFilterClick : RequestScreenEvents()
    data object OnPendingFilterClick : RequestScreenEvents()
    data object OnApprovedFilterClick : RequestScreenEvents()
    data object OnRejectedFilterClick : RequestScreenEvents()
    data object DismissIdCard : RequestScreenEvents()
    data object DismissWarningDialog : RequestScreenEvents()
    data object LoadRequest : RequestScreenEvents()
}