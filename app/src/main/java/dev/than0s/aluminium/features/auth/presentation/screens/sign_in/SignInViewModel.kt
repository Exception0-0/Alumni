package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.auth.domain.use_cases.SignInUseCase
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) :
    ViewModel() {
    val signInParam = mutableStateOf(EmailAuthParam())
    val signInState = mutableStateOf(SignInState())

    private fun onEmailChange(email: String) {
        signInParam.value = signInParam.value.copy(email = email)
    }

    private fun onPasswordChange(password: String) {
        signInParam.value = signInParam.value.copy(password = password)
    }

    private fun onSignInClick(
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            signInState.value = signInState.value.copy(isLoading = true)
            when (val result = signInUseCase.invoke(signInParam.value)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar("Error: ${result.value}")
                }

                is Either.Right -> {
                    onSuccess()
                    SnackbarController.showSnackbar("Signed in successfully")
                }
            }
            signInState.value = signInState.value.copy(isLoading = false)
            onComplete()
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
                    onSuccess = event.onSuccess,
                    onComplete = event.onComplete
                )
            }
        }
    }
}