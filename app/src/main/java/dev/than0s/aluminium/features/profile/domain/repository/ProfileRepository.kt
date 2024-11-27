package dev.than0s.aluminium.features.profile.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User

interface ProfileRepository {
    suspend fun getUserProfile(userId: String): Either<Failure, User?>
    suspend fun setUserProfile(profile: User): Either<Failure, Unit>
    suspend fun setContactInfo(contactInfo: ContactInfo): Either<Failure, Unit>
    suspend fun getContactInfo(userId: String): Either<Failure, ContactInfo?>
    suspend fun getAboutInfo(userId: String): Either<Failure, AboutInfo>
    suspend fun getUserPosts(userId: String): Either<Failure, List<Post>>
}