package dev.than0s.aluminium.features.settings.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.dataObjects
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ProfileDataSource {
    val userProfile: Flow<User?>
    suspend fun updateProfile(user: User)
}

class FirebaseProfileDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) : ProfileDataSource {
    override val userProfile: Flow<User?>
        get() = try {
            store.collection(profile).document(auth.currentUser!!.uid).dataObjects()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }

    override suspend fun updateProfile(user: User) {
        try {
            store.collection(profile).document(auth.currentUser!!.uid).set(user).await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

}

const val profile = "profile"