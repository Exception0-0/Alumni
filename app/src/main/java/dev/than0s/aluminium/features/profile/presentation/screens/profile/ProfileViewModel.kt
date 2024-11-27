package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.domain.use_cases.GetAboutInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.GetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.GetUserPostsUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.GetUserUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetContactInfoUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val profileUseCase: GetUserUseCase,
    private val updateProfileUseCase: SetProfileUseCase,
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val setContactInfoUseCase: SetContactInfoUseCase,
    private val getAboutInfoUseCase: GetAboutInfoUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase
) : ViewModel() {
    var userProfile by mutableStateOf(User())
    var contactInfo by mutableStateOf(ContactInfo())
    var aboutInfo by mutableStateOf(AboutInfo())
    var editUserProfile by mutableStateOf(User())
    var editContactInfo by mutableStateOf(ContactInfo())
    var postsList by mutableStateOf(emptyList<Post>())

    val profileScreenArgs = savedStateHandle.toRoute<Screen.ProfileScreen>()

    init {
        loadProfile()
        getContactInfo()
        getAboutInfo()
        getUserPosts()
    }

    fun loadProfile() {
        viewModelScope.launch {
            when (val result = profileUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> result.value?.let {
                    userProfile = it
                    editUserProfile = it
                }
            }
        }
    }

    fun getContactInfo() {
        viewModelScope.launch {
            when (val result = getContactInfoUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> result.value.let {
                    contactInfo = it ?: ContactInfo()
                    editContactInfo = it ?: ContactInfo()
                }
            }
        }
    }

    fun getUserPosts() {
        viewModelScope.launch {
            when (val result = getUserPostsUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    postsList = result.value
                }
            }
        }
    }

    fun getAboutInfo() {
        viewModelScope.launch {
            when (val result = getAboutInfoUseCase.invoke(profileScreenArgs.userId)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> result.value.let {
                    aboutInfo = it
                }
            }
        }
    }

    fun onUpdateProfileClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = updateProfileUseCase.invoke(editUserProfile)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Profile updated successfully")
                    onSuccess()
                    loadProfile()
                }
            }
        }
    }

    fun onContactInfoUpdateClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = setContactInfoUseCase.invoke(editContactInfo)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Contact info updated successfully")
                    onSuccess()
                    getContactInfo()
                }
            }
        }
    }

    fun onFirstNameChange(name: String) {
        editUserProfile = editUserProfile.copy(firstName = name)
    }

    fun onLastNameChange(name: String) {
        editUserProfile = editUserProfile.copy(lastName = name)
    }

    fun onBioChange(bio: String) {
        editUserProfile = editUserProfile.copy(bio = bio)
    }

    fun onProfileImageChange(image: Uri) {
        editUserProfile = editUserProfile.copy(profileImage = image)
    }

    fun onCoverImageChange(image: Uri) {
        editUserProfile = editUserProfile.copy(coverImage = image)
    }

    fun onEmailChange(email: String) {
        editContactInfo = editContactInfo.copy(email = email)
    }

    fun onMobileChange(mobile: String) {
        editContactInfo = editContactInfo.copy(mobile = mobile)
    }

    fun onSocialHandleChange(handle: String) {
        editContactInfo = editContactInfo.copy(socialHandles = handle)
    }
}