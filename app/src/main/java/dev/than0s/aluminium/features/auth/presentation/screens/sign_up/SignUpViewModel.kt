package dev.than0s.aluminium.features.auth.presentation.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: EmailSignUpUseCase) :
    ViewModel() {
    val param = mutableStateOf(Param())
    val showDialog = mutableStateOf(false)

    fun onEmailChange(email: String) {
        param.value = param.value.copy(email = email)
    }

    fun onDialogDismiss() {
        showDialog.value = false
    }

    fun onCategoryClick() {
        showDialog.value = true
    }

    fun onCategoryChange(category: String) {
        param.value = param.value.copy(category = category)
    }

    fun onIdChange(id: String) {
        param.value = param.value.copy(id = id)
    }

    fun onFirstNameChange(firstName: String) {
        param.value = param.value.copy(firstName = firstName)
    }

    fun onMiddleNameChange(middleName: String) {
        param.value = param.value.copy(middleName = middleName)
    }

    fun onLastNameChange(lastName: String) {
        param.value = param.value.copy(lastName = lastName)
    }

    fun onBatchChange(batch: String) {
        param.value = param.value.copy(batch = batch)
    }

    fun onRegisterClick() {
//        viewModelScope.launch {
//            when (val result = signUpUseCase.invoke(param.value)) {
//                is Either.Left -> TODO("show error message")
//                is Either.Right -> restartApp()
//            }
//        }
    }
}