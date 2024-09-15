package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.domain.use_cases.SignInUseCase
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) :
    ViewModel() {
    val signInParam = mutableStateOf(EmailAuthParam())

    fun onEmailChange(email: String) {
        signInParam.value = signInParam.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        signInParam.value = signInParam.value.copy(password = password)
    }

    fun onSignInClick(restartApp: () -> Unit) {
        viewModelScope.launch {
            when (signInUseCase.invoke(signInParam.value)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> restartApp()
            }
        }
    }

    fun onForgetPasswordClick() {

    }
}