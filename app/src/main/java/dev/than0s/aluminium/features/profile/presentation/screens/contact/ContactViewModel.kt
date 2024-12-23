package dev.than0s.aluminium.features.profile.presentation.screens.contact

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
import dev.than0s.aluminium.features.profile.domain.use_cases.GetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetContactInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val setContactInfoUseCase: SetContactInfoUseCase,
) : ViewModel() {

    var userId = ""
    var screenState by mutableStateOf(ContactState())

    private fun loadContactInfo() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getContactInfoUseCase(userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(contactInfo = result.data!!)
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onContactInfoUpdateClick() {
        viewModelScope.launch {
            val setContactInfoResult = setContactInfoUseCase(screenState.contactInfo)

            setContactInfoResult.let {
                screenState = screenState.copy(
                    emailError = it.emailError,
                    mobileError = it.mobileError,
                    socialHandleError = it.socialHandel
                )
            }

            when (setContactInfoResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = setContactInfoResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_update_contact_info)
                        )
                    )
                }

                null -> {}
            }
        }
    }


    private fun onEmailChange(email: String) {
        screenState = screenState.copy(
            contactInfo = screenState.contactInfo.copy(email = email)
        )
    }

    private fun onMobileChange(mobile: String) {
        screenState = screenState.copy(
            contactInfo = screenState.contactInfo.copy(mobile = mobile)
        )
    }

    private fun onSocialHandleChange(handle: String) {
        screenState = screenState.copy(
            contactInfo = screenState.contactInfo.copy(socialHandles = handle)
        )
    }

    private fun onContactEditClick() {
        screenState = screenState.copy(updateDialog = true)
    }

    private fun onContactUpdateDialogDismissRequest() {
        screenState = screenState.copy(updateDialog = false)
    }

    fun onEvent(event: ContactEvents) {
        when (event) {
            is ContactEvents.LoadContactInfo -> {
                loadContactInfo()
            }

            is ContactEvents.OnUpdateClick -> {
                onContactInfoUpdateClick()
            }

            is ContactEvents.OnEmailChanged -> {
                onEmailChange(event.email)
            }

            is ContactEvents.OnMobileChanged -> {
                onMobileChange(event.mobile)
            }

            is ContactEvents.OnSocialChanged -> {
                onSocialHandleChange(event.handel)
            }

            is ContactEvents.OnUpdateDialogDismissRequest -> {
                onContactUpdateDialogDismissRequest()
            }

            is ContactEvents.OnContactInfoEditClick -> {
                onContactEditClick()
            }
        }
    }
}