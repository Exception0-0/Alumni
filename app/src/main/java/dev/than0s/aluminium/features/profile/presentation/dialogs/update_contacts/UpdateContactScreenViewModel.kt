package dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.ContactInfo
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.profile.domain.use_cases.GetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetContactInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateContactScreenViewModel @Inject constructor(
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val setContactInfoUseCase: SetContactInfoUseCase,
) : ViewModel() {

    var screenState by mutableStateOf(UpdateContactScreenState())

    init {
        loadContactInfo()
    }

    private fun loadContactInfo() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getContactInfoUseCase(currentUserId!!)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        contactInfo = result.data ?: ContactInfo(userId = currentUserId!!)
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onContactInfoUpdateClick(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            screenState = screenState.copy(isUpdating = true)

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
                    onSuccess()
                }

                null -> {}
            }
            screenState = screenState.copy(isUpdating = false)
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

    fun onEvent(event: UpdateContactScreenEvents) {
        when (event) {
            is UpdateContactScreenEvents.OnEmailChanged -> onEmailChange(event.email)
            is UpdateContactScreenEvents.OnMobileChanged -> onMobileChange(event.mobile)
            is UpdateContactScreenEvents.OnSocialChanged -> onSocialHandleChange(event.handel)
            is UpdateContactScreenEvents.OnUpdateClick -> onContactInfoUpdateClick(
                onSuccess = event.onSuccess
            )
        }
    }
}