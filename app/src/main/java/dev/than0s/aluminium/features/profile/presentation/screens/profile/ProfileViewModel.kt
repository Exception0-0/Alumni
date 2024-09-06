package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.domain.use_cases.AccountSignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val signOutUseCase: AccountSignOutUseCase) :
    ViewModel() {
    fun onSignOutClick(restartApp: () -> Unit) {
        viewModelScope.launch {
            when (signOutUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> restartApp()
            }
        }
    }
}