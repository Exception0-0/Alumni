package dev.than0s.aluminium.features.settings.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import javax.inject.Inject

interface StorageDataSource {
    suspend fun getProfileImage(): Uri
    suspend fun setProfileImage(image: Uri)
}

class FirebaseStorageDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val cloud: FirebaseStorage
) : StorageDataSource {

    override suspend fun setProfileImage(image: Uri) {
        try {
            cloud.reference.child("$profileImages/${auth.currentUser!!.uid}").putFile(image)
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getProfileImage(): Uri {
        return try {
            cloud.reference.child("$profileImages/${auth.currentUser!!.uid}").downloadUrl.await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

}

const val profileImages = "profile_images"