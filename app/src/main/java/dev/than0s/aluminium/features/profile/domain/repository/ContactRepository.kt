package dev.than0s.aluminium.features.profile.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.domain.data_class.ContactInfo

interface ContactRepository {
    suspend fun setContactInfo(contactInfo: ContactInfo): SimpleResource
    suspend fun getContactInfo(userId: String): Resource<ContactInfo?>
}