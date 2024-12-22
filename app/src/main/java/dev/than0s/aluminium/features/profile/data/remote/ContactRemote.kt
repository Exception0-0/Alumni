package dev.than0s.aluminium.features.profile.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import dev.than0s.aluminium.core.data.remote.CONTACT_INFO
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ContactRemote {
    suspend fun setContactInfo(contactInfo: ContactInfo)
    suspend fun getContactInfo(userId: String): ContactInfo
}

class ContactRemoteImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
) : ContactRemote {
    override suspend fun setContactInfo(contactInfo: ContactInfo) {
        try {
            store.collection(CONTACT_INFO)
                .document(auth.currentUser!!.uid)
                .set(contactInfo)
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getContactInfo(userId: String): ContactInfo {
        return try {
            store.collection(CONTACT_INFO)
                .document(userId)
                .get()
                .await()
                .toObject() ?: ContactInfo()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}