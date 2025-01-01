package dev.than0s.aluminium.features.profile.presentation.screens.contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.domain.data_class.ContactInfo
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.profile.domain.use_cases.GetContactInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getContactInfoUseCase: GetContactInfoUseCase,
) : ViewModel() {

    val contactScreenArgs = savedStateHandle.toRoute<Screen.ProfileTabScreen.ContactScreen>()
    var screenState by mutableStateOf(ContactState())

    init {
        loadContactInfo()
    }

    private fun loadContactInfo() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getContactInfoUseCase(contactScreenArgs.userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        contactInfo = result.data ?: ContactInfo(userId = contactScreenArgs.userId)
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    fun onEvent(event: ContactEvents) {
        when (event) {
            is ContactEvents.LoadContactInfo -> {
                loadContactInfo()
            }
        }
    }
}