package dev.than0s.aluminium.features.profile.data.mapper

import android.net.Uri
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User

fun User.toRawUser(): RawUser = RawUser(
    firstName = firstName,
    lastName = lastName,
    bio = bio
)

fun RawUser.toUser(profileImage: Uri?, coverImage: Uri?): User = User(
    firstName = firstName,
    lastName = lastName,
    bio = bio,
    profileImage = profileImage,
    coverImage = coverImage
)

data class RawUser(
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)