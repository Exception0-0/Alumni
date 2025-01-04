package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

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
import dev.than0s.aluminium.features.auth.domain.use_cases.SignInUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) :
    ViewModel() {
    var screenState by mutableStateOf(SignInState())

    private fun onEmailChange(email: String) {
        screenState = screenState.copy(email = email)
    }

    private fun onPasswordChange(password: String) {
        screenState = screenState.copy(password = password)
    }

    private fun onPasswordVisibilityChange() {
        screenState = screenState.copy(isPasswordVisible = !screenState.isPasswordVisible)
    }

    private fun onSignInClick(
        restartApp: () -> Unit,
    ) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val signInResult = signInUseCase(
                email = screenState.email,
                password = screenState.password,
            )

            signInResult.let {
                screenState = screenState.copy(
                    emailError = it.emailError,
                    passwordError = it.passwordError
                )
            }

            when (signInResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = signInResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.successfully_login)
                        )
                    )
                    restartApp()
                }

                null -> {}
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnEmailChanged -> {
                onEmailChange(event.email)
            }

            is SignInEvents.OnPasswordChange -> {
                onPasswordChange(event.password)
            }

            is SignInEvents.OnSignInClick -> {
                onSignInClick(
                    restartApp = event.restartApp,
                )
            }

            is SignInEvents.OnPasswordVisibilityChange -> {
                onPasswordVisibilityChange()
            }
        }
    }
}