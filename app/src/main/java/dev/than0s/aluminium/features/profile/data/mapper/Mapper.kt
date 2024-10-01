package dev.than0s.aluminium.features.profile.data.mapper

import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo

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