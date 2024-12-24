package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

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
import dev.than0s.aluminium.features.auth.domain.use_cases.ForgetPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val useCase: ForgetPasswordUseCase) :
    ViewModel() {
    var state by mutableStateOf(ForgetPasswordState())

    private fun onForgetPasswordClick(
        popScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            val forgetPasswordResult = useCase(state.email)

            forgetPasswordResult.let {
                state = state.copy(
                    emailError = it.emailError
                )
            }

            when (forgetPasswordResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = forgetPasswordResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.forget_password_success)
                        )
                    )
                    popScreen()
                }

                null -> {}
            }
        }
    }

    private fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onEvent(
        event: ForgetPasswordEvents
    ) {
        when (event) {
            is ForgetPasswordEvents.OnForgetPasswordClick -> {
                onForgetPasswordClick(
                    popScreen = event.popScreen
                )
            }

            is ForgetPasswordEvents.OnEmailChange -> {
                onEmailChange(
                    email = event.email
                )
            }
        }
    }
}