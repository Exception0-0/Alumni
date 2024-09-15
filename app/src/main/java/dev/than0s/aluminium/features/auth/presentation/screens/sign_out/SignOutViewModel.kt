package dev.than0s.aluminium.features.auth.presentation.screens.sign_out

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.domain.use_cases.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignOutViewModel @Inject constructor(private val useCase: SignOutUseCase) : ViewModel() {
    fun signOut(restartApp: () -> Unit) {
        viewModelScope.launch {
            when (val result = useCase.invoke(Unit)) {
                is Either.Left -> {}
                is Either.Right -> {
                    restartApp()
                }
            }
        }
    }
}