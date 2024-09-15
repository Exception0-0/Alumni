package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.splash.domain.use_cases.AccountHasUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val accountHasUserUseCase: AccountHasUserUseCase) :
    ViewModel() {
    fun loadScreen(onSuccessful: (Boolean) -> Unit) {
        viewModelScope.launch {
            when (val hasUser = accountHasUserUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> onSuccessful(hasUser.value)
            }
        }
    }
}