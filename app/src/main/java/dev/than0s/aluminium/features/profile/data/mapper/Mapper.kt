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

fun ContactInfo.toRawContactInfo(): RawContactInfo = RawContactInfo(
    mobile = mobile,
    socialHandles = socialHandles
)

fun RawContactInfo.toContactInfo(email: String): ContactInfo = ContactInfo(
    email = email,
    mobile = mobile,
    socialHandles = socialHandles
)

data class RawContactInfo(
    val mobile: String? = null,
    val socialHandles: String? = null
)

data class RawUser(
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
)