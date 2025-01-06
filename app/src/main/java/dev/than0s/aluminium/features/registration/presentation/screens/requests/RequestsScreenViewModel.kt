package dev.than0s.aluminium.features.registration.presentation.screens.requests

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.registration.domain.use_cases.AcceptRegistrationRequestUseCase
import dev.than0s.aluminium.features.registration.domain.use_cases.GetRegistrationRequestsUseCase
import dev.than0s.aluminium.features.registration.domain.use_cases.RejectRegistrationRequestUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsScreenViewModel @Inject constructor(
    private val getRegistrationRequestsUseCase: GetRegistrationRequestsUseCase,
    private val acceptRegistrationRequestUseCase: AcceptRegistrationRequestUseCase,
    private val rejectRegistrationRequestUseCase: RejectRegistrationRequestUseCase
) :
    ViewModel() {
    var screenState by mutableStateOf(RequestScreenState())

    init {
        loadRequestList()
    }

    private fun loadRequestList() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getRegistrationRequestsUseCase()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(requestsList = result.data!!)
                    filterRequestsOnFilters()
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onAcceptClick(registrationRequestId: String) {
        viewModelScope.launch {
            screenState = screenState.copy(isUpdating = true)
            when (val result = acceptRegistrationRequestUseCase(registrationRequestId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.request_accepted_successfully)
                        )
                    )
                    dismissWarningDialog()
                    loadRequestList()
                }
            }
            screenState = screenState.copy(isUpdating = false)
        }
    }

    private fun onRejectedClick(registrationRequestId: String) {
        viewModelScope.launch {
            screenState = screenState.copy(isUpdating = true)
            when (val result = rejectRegistrationRequestUseCase(registrationRequestId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.request_rejected_successfully)
                        )
                    )
                    dismissWarningDialog()
                    loadRequestList()
                }
            }
            screenState = screenState.copy(isUpdating = false)
        }
    }

    private fun onSearchTextChange(text: String) {
        screenState = screenState.copy(searchText = text)
        filterRequestsOnSearchText()
    }

    private fun showWarningDialog(requestId: String, accepted: Boolean) {
        screenState = screenState.copy(requestSelection = Pair(requestId, accepted))
    }

    private fun dismissWarningDialog() {
        screenState = screenState.copy(requestSelection = null)
    }

    private fun showIdCard(uri: Uri) {
        screenState = screenState.copy(selectedIdCard = uri)
    }

    private fun dismissIdCard() {
        screenState = screenState.copy(selectedIdCard = null)
    }

    private fun onAllFilterClick() {
        screenState = screenState.copy(
            pendingFilter = !screenState.allFilter,
            approvedFilter = !screenState.allFilter,
            rejectedFilter = !screenState.allFilter,
            allFilter = !screenState.allFilter
        )
        filterRequestsOnFilters()
    }

    private fun onPendingFilterClick() {
        screenState = screenState.copy(pendingFilter = !screenState.pendingFilter)
        shouldAllFilterCheck()
        filterRequestsOnFilters()
    }

    private fun onApprovedFilterClick() {
        screenState = screenState.copy(approvedFilter = !screenState.approvedFilter)
        shouldAllFilterCheck()
        filterRequestsOnFilters()
    }

    private fun onRejectFilterClick() {
        screenState = screenState.copy(rejectedFilter = !screenState.rejectedFilter)
        shouldAllFilterCheck()
        filterRequestsOnFilters()
    }

    private fun shouldAllFilterCheck() {
        if ((!screenState.approvedFilter || !screenState.rejectedFilter || !screenState.pendingFilter)
            && screenState.allFilter
        ) {
            screenState = screenState.copy(allFilter = false)
        } else if ((screenState.approvedFilter && screenState.rejectedFilter && screenState.pendingFilter)
            && !screenState.allFilter
        ) {
            screenState = screenState.copy(allFilter = true)
        }
    }

    private fun filterRequestsOnFilters() {
        screenState = screenState.copy(
            filteredList = screenState.requestsList.filter {
                when (it.status.approvalStatus) {
                    null -> screenState.pendingFilter
                    true -> screenState.approvedFilter
                    false -> screenState.rejectedFilter
                }
            }
        )
    }

    private fun filterRequestsOnSearchText() {
        screenState = screenState.copy(
            filteredList = screenState.requestsList.filter {
                it.toString().contains(screenState.searchText,ignoreCase = true)
            }
        )
    }

    fun onEvent(event: RequestScreenEvents) {
        when (event) {
            is RequestScreenEvents.OnAcceptClick -> {
                onAcceptClick(event.requestId)
            }

            is RequestScreenEvents.OnRejectClick -> {
                onRejectedClick(event.requestId)
            }

            is RequestScreenEvents.LoadRequest -> {
                loadRequestList()
            }

            is RequestScreenEvents.ShowWarningDialog -> {
                showWarningDialog(event.formId, event.accepted)
            }

            is RequestScreenEvents.DismissWarningDialog -> {
                dismissWarningDialog()
            }

            is RequestScreenEvents.ShowIdCard -> {
                showIdCard(event.imageUri)
            }

            is RequestScreenEvents.DismissIdCard -> {
                dismissIdCard()
            }

            is RequestScreenEvents.OnAllFilterClick -> onAllFilterClick()
            is RequestScreenEvents.OnApprovedFilterClick -> onApprovedFilterClick()
            is RequestScreenEvents.OnPendingFilterClick -> onPendingFilterClick()
            is RequestScreenEvents.OnRejectedFilterClick -> onRejectFilterClick()
            is RequestScreenEvents.OnSearchTextChanged -> onSearchTextChange(event.text)
        }
    }
}