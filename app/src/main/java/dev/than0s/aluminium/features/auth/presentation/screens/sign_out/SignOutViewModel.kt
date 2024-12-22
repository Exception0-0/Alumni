package dev.than0s.aluminium.features.auth.presentation.screens.sign_out

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.features.auth.domain.use_cases.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private fun signOut(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signOutUseCase()) {
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
                            message = UiText.StringResource(R.string.successfully_log_out)
                        )
                    )
                    onSuccess()
                }
            }
        }
    }

    fun onEvent(
        event: SignOutEvents
    ) {
        when (event) {
            is SignOutEvents.SignOut -> {
                signOut(
                    onSuccess = event.onSuccess,
                )
            }
        }
    }
}