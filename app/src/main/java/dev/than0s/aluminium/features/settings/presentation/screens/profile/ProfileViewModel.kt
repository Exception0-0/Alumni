package dev.than0s.aluminium.features.settings.presentation.screens.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.aluminium.features.settings.domain.use_cases.AccountSignOutUseCase
import dev.than0s.aluminium.features.settings.domain.use_cases.DownloadProfileImageUseCase
import dev.than0s.aluminium.features.settings.domain.use_cases.GetUserUseCase
import dev.than0s.aluminium.features.settings.domain.use_cases.SetProfileUseCase
import dev.than0s.aluminium.features.settings.domain.use_cases.UpdateProfileImageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: AccountSignOutUseCase,
    private val profileUseCase: GetUserUseCase,
    private val updateProfileUseCase: SetProfileUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val downloadImageUseCase: DownloadProfileImageUseCase
) :
    ViewModel() {
    var userProfile by mutableStateOf(User())

    init {
        viewModelScope.launch {
            when (val result = profileUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> result.value?.let {
                    userProfile = it
                }
            }
        }

        viewModelScope.launch {
            when (val result = downloadImageUseCase.invoke(Unit)) {
                is Either.Left -> println("no image")
                is Either.Right -> onProfileImageChange(result.value)
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            when (val result = updateProfileUseCase.invoke(userProfile)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> println("profile update successfully")
            }
        }

        viewModelScope.launch {
            when (val result = updateProfileImageUseCase.invoke(userProfile.profileImage)) {
                is Either.Left -> println(result.value.message)
                is Either.Right -> println("profile image update successfully")
            }
        }
    }

    fun onSignOutClick(restartApp: () -> Unit) {
        viewModelScope.launch {
            when (signOutUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> restartApp()
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
