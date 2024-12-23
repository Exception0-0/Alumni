package dev.than0s.aluminium.features.registration.presentation.screens.registration_requests

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
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.use_cases.AcceptRegistrationRequest
import dev.than0s.aluminium.features.registration.domain.use_cases.RegistrationRequestListUseCase
import dev.than0s.aluminium.features.registration.domain.use_cases.RejectRegistrationRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestsUseCase: RegistrationRequestListUseCase,
    private val acceptedUseCase: AcceptRegistrationRequest,
    private val rejectedUserCase: RejectRegistrationRequest
) :
    ViewModel() {
    var screenState by mutableStateOf(RequestScreenState())

    init {
        loadRequestList()
    }

    private fun loadRequestList() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = requestsUseCase()) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(requestsList = result.data!!)
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onAcceptClick(form: RegistrationForm) {
        viewModelScope.launch {
            screenState = screenState.copy(isDialogLoading = true)
            when (val result = acceptedUseCase(form)) {
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
                }
            }
            screenState = screenState.copy(isDialogLoading = false)
        }
    }

    private fun onRejectedClick(form: RegistrationForm) {
        viewModelScope.launch {
            screenState = screenState.copy(isDialogLoading = true)
            when (val result = rejectedUserCase(form)) {
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
                }
            }
            screenState = screenState.copy(isDialogLoading = false)
        }
    }

    private fun showWarningDialog(formId: String, accepted: Boolean) {
        screenState = screenState.copy(requestSelection = Pair(formId, accepted))
    }

    private fun hideWarningDialog() {
        screenState = screenState.copy(requestSelection = null)
    }

    private fun showIdCard(formId: String) {
        screenState = screenState.copy(idCardSelection = formId)
    }

    private fun hideIdCard() {
        screenState = screenState.copy(idCardSelection = null)
    }

    fun onEvent(event: RequestScreenEvents) {
        when (event) {
            is RequestScreenEvents.OnAcceptClick -> {
                onAcceptClick(event.form)
            }

            is RequestScreenEvents.OnRejectClick -> {
                onRejectedClick(event.form)
            }

            is RequestScreenEvents.OnLoad -> {
                loadRequestList()
            }

            is RequestScreenEvents.OnShowDialog -> {
                showWarningDialog(event.formId, event.accepted)
            }

            is RequestScreenEvents.OnDismissDialog -> {
                hideWarningDialog()
            }

            is RequestScreenEvents.OnShowIdCard -> {
                showIdCard(event.formId)
            }

            is RequestScreenEvents.OnHideIdCard -> {
                hideIdCard()
            }
        }
    }
}