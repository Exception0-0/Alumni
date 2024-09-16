package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.splash.domain.use_cases.HasUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val hasUserUseCase: HasUserUseCase) :
    ViewModel() {
    fun loadScreen(onSuccessful: (Boolean) -> Unit) {
        viewModelScope.launch {
            when (val hasUser = hasUserUseCase.invoke(Unit)) {
                is Either.Left -> println("has user failed ${hasUser.value}")
                is Either.Right -> onSuccessful(hasUser.value)
            }
        }
    }
}