package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.use_cases.GetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.GetUserUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: GetUserUseCase,
    private val updateProfileUseCase: SetProfileUseCase,
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val setContactInfoUseCase: SetContactInfoUseCase
) : ViewModel() {
    var userProfile by mutableStateOf(User())
    var contactInfo by mutableStateOf(ContactInfo())

    init {
        loadProfile()
        getContactInfo()
    }

    fun loadProfile() {
        viewModelScope.launch {
            when (val result = profileUseCase.invoke(Unit)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)

                }

                is Either.Right -> result.value?.let {
                    userProfile = it
                }
            }
        }
    }

    fun getContactInfo() {
        viewModelScope.launch {
            when (val result = getContactInfoUseCase.invoke(Unit)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> result.value.let {
                    contactInfo = it
                }
            }
        }
    }

    fun onUpdateProfileClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = updateProfileUseCase.invoke(userProfile)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Profile updated successfully")
                    onSuccess()
                }
            }
        }
    }

    fun onContactInfoUpdateClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = setContactInfoUseCase.invoke(contactInfo)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Contact info updated successfully")
                    onSuccess()
                }
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

    fun onEmailChange(email: String) {
        contactInfo = contactInfo.copy(email = email)
    }

    fun onMobileChange(mobile: String) {
        contactInfo = contactInfo.copy(mobile = mobile)
    }

    fun onSocialHandleChange(handle: String) {
        contactInfo = contactInfo.copy(socialHandles = handle)
    }
}