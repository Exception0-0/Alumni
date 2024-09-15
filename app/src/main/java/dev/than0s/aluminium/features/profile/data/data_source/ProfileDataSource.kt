package dev.than0s.aluminium.features.profile.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ProfileDataSource {
    suspend fun getUserProfile(): User?
    suspend fun setUserProfile(user: User)
    suspend fun setProfileImage(image: Uri)
    suspend fun getProfileImage(): Uri
}

class ProfileDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
) : ProfileDataSource {
    override suspend fun getUserProfile(): User? {
        return try {
            store.collection(PROFILE).document(auth.currentUser!!.uid)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun setUserProfile(user: User) {
        try {
            store.collection(PROFILE).document(auth.currentUser!!.uid).set(
                hashMapOf(
                    "id" to user.id,
                    "firstName" to user.firstName,
                    "lastName" to user.lastName,
                    "bio" to user.bio,
                )
            ).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun setProfileImage(image: Uri) {
        try {
            cloud.reference.child("$PROFILE_IMAGE/${auth.currentUser!!.uid}").putFile(image)
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getProfileImage(): Uri {
        return try {
            cloud.reference.child("$PROFILE_IMAGE/${auth.currentUser!!.uid}").downloadUrl.await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val PROFILE_IMAGE = "profile_images"
        private const val PROFILE = "profile"
    }
}