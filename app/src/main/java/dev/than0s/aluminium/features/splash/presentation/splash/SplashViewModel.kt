package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.setCurrentUserId
import dev.than0s.aluminium.core.setCurrentUserRole
import dev.than0s.aluminium.features.splash.domain.use_cases.GetCurrentUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getCurrentUser: GetCurrentUserUseCase) :
    ViewModel() {
    fun loadScreen(onSuccessful: (String?) -> Unit) {
        viewModelScope.launch {
            when (val result = getCurrentUser.invoke(Unit)) {
                is Either.Left -> SnackbarController.showSnackbar(result.value.message)
                is Either.Right -> {
                    setCurrentUserId(result.value.userId)
                    setCurrentUserRole(result.value.role)
                    onSuccessful(result.value.role)
                }
            }
        }
    }
}