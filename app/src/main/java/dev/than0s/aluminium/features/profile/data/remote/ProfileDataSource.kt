package dev.than0s.aluminium.features.profile.data.remote

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.than0s.aluminium.core.data.remote.COLLEGE_INFO
import dev.than0s.aluminium.core.data.remote.COVER_IMAGE
import dev.than0s.aluminium.core.data.remote.PROFILE
import dev.than0s.aluminium.core.data.remote.PROFILE_IMAGE
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.data_class.AboutInfo
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.domain.util.isLocalUri
import dev.than0s.aluminium.features.profile.data.mapper.RemoteAboutInfo
import dev.than0s.aluminium.features.profile.data.mapper.RemoteUser
import dev.than0s.aluminium.features.profile.data.mapper.toAboutInfo
import dev.than0s.aluminium.features.profile.data.mapper.toRemoteUser
import dev.than0s.aluminium.features.profile.data.mapper.toUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ProfileDataSource {
    suspend fun setUserProfile(user: User)
    suspend fun getAboutInfo(userId: String): AboutInfo
    suspend fun getUserProfile(userId: String): User?
}

class ProfileDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
) : ProfileDataSource {

    override suspend fun setUserProfile(user: User) {
        try {
            val profileImageUri = uploadProfileImage(user.profileImage)
            val coverImageUri = uploadCoverImage(user.coverImage)

            store.collection(PROFILE)
                .document(auth.currentUser!!.uid)
                .set(
                    user.copy(
                        profileImage = profileImageUri,
                        coverImage = coverImageUri,
                    ).toRemoteUser()
                )
                .await()

        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getAboutInfo(userId: String): AboutInfo {
        return try {
            store.collection(COLLEGE_INFO)
                .document(userId)
                .get()
                .await()
                .toObject<RemoteAboutInfo>()!!
                .toAboutInfo()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getUserProfile(userId: String): User? {
        return try {
            store.collection(PROFILE)
                .document(userId)
                .get()
                .await()
                .toObject(RemoteUser::class.java)
                ?.toUser()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun uploadProfileImage(imageUri: Uri): Uri {
        return try {
            if (isLocalUri(imageUri)) {
                cloud.reference
                    .child("$PROFILE_IMAGE/${auth.currentUser!!.uid}/0")
                    .putFile(imageUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            } else imageUri
        } catch (e: StorageException) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun uploadCoverImage(imageUri: Uri): Uri {
        return try {
            if (isLocalUri(imageUri)) {
                cloud.reference
                    .child("$COVER_IMAGE/${auth.currentUser!!.uid}/0")
                    .putFile(imageUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            } else imageUri
        } catch (e: StorageException) {
            throw ServerException(e.message.toString())
        }
    }
}