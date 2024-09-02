package dev.than0s.aluminium.features.auth.presentation.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignUpUseCase
import dev.than0s.mydiary.core.data_class.EmailAuthParam
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: EmailSignUpUseCase) :
    ViewModel() {
    val signUpParam = mutableStateOf(EmailAuthParam())

    fun onEmailChange(email: String) {
        signUpParam.value = signUpParam.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        signUpParam.value = signUpParam.value.copy(password = password)
    }

    fun onSignInClick(restartApp: () -> Unit) {
        viewModelScope.launch {
            when (val result = signUpUseCase.invoke(signUpParam.value)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> restartApp()
            }
        }
    }

    fun onForgetPasswordClick() {

    }
}