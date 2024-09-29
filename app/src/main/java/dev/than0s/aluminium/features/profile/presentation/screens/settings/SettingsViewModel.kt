package dev.than0s.aluminium.features.profile.presentation.screens.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.use_cases.GetUserUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileUseCase: GetUserUseCase,
    private val updateProfileUseCase: SetProfileUseCase,
) :
    ViewModel() {
    var userProfile by mutableStateOf(User())

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            when (val result = profileUseCase.invoke(Unit)) {
                is Either.Left -> println("load profile failed ${result.value}")
                is Either.Right -> result.value?.let {
                    userProfile = it
                }
            }
        }
    }

    fun onUpdateProfileClick() {
        viewModelScope.launch {
            when (val result = updateProfileUseCase.invoke(userProfile)) {
                is Either.Left -> println("profile update failed ${result.value}")
                is Either.Right -> println("profile update successfully")
            }
        }
    }

    fun onFirstNameChange(name: String) {
        userProfile = userProfile.copy(firstName = name)
    }

    fun onLastNameChange(name: String) {
        userProfile = userProfile.copy(lastName = name)
    }

    fun onBioChange(bio: String) {
        userProfile = userProfile.copy(bio = bio)
    }

    fun onProfileImageChange(image: Uri) {
        userProfile = userProfile.copy(profileImage = image)
    }
}
