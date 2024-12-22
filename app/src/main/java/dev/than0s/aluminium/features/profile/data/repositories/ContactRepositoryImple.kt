package dev.than0s.aluminium.features.profile.data.repositories

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.profile.data.remote.ContactRemote
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImple @Inject constructor(
    private val dataSource: ContactRemote
) : ContactRepository {
    override suspend fun setContactInfo(contactInfo: ContactInfo): SimpleResource {
        return try {
            dataSource.setContactInfo(contactInfo)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getContactInfo(userId: String): Resource<ContactInfo> {
        return try {
            Resource.Success(dataSource.getContactInfo(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}