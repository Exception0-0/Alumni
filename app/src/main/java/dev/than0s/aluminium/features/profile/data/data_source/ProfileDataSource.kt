package dev.than0s.aluminium.features.profile.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.than0s.aluminium.features.profile.data.mapper.RawUser
import dev.than0s.aluminium.features.profile.data.mapper.toRawUser
import dev.than0s.aluminium.features.profile.data.mapper.toUser
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ProfileDataSource {
    suspend fun getUserProfile(userId: String): User?
    suspend fun setUserProfile(user: User)
    suspend fun setContactInfo(contactInfo: ContactInfo)
    suspend fun getContactInfo(userId: String): ContactInfo?
}

class ProfileDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
) : ProfileDataSource {
    override suspend fun getUserProfile(userId: String): User? {
        return try {
            store.collection(PROFILE)
                .document(userId)
                .get()
                .await()
                .toObject(RawUser::class.java)
                ?.toUser(
                    profileImage = getUserProfileImage(userId),
                    coverImage = getUserCoverImage(userId)
                )
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun setUserProfile(user: User) {
        try {
            store.collection(PROFILE)
                .document(auth.currentUser!!.uid)
                .set(user.toRawUser())
                .await()

            user.profileImage?.let {
                cloud.reference
                    .child("$PROFILE_IMAGE/${auth.currentUser!!.uid}/0")
                    .putFile(user.profileImage)
                    .await()
            }

            user.coverImage?.let {
                cloud.reference
                    .child("$COVER_IMAGE/${auth.currentUser!!.uid}/0")
                    .putFile(user.coverImage)
                    .await()
            }
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun setContactInfo(contactInfo: ContactInfo) {
        try {
            store.collection(CONTACT_INFO)
                .document(auth.currentUser!!.uid)
                .set(contactInfo)
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getContactInfo(userId: String): ContactInfo? {
        return try {
            store.collection(CONTACT_INFO)
                .document(userId)
                .get()
                .await()
                .toObject()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getUserProfileImage(userId:String): Uri? {
        return try {
            cloud.reference
                .child("$PROFILE_IMAGE/${userId}/0")
                .downloadUrl
                .await()
        } catch (e: StorageException) {
            if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                return null
            }
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getUserCoverImage(userId:String): Uri? {
        return try {
            cloud.reference
                .child("$COVER_IMAGE/${userId}/0")
                .downloadUrl
                .await()
        } catch (e: StorageException) {
            if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                return null
            }
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val PROFILE_IMAGE = "profile_images"
        private const val COVER_IMAGE = "user_cover_image"
        private const val PROFILE = "profile"
        private const val CONTACT_INFO = "contact_info"
    }
}