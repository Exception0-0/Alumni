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
    suspend fun setFile(image: Uri, path: String)
    suspend fun getFile(path: String): Uri
}

class FirebaseStorageDataSourceImple @Inject constructor(
    private val cloud: FirebaseStorage
) : StorageDataSource {

    override suspend fun setFile(image: Uri, path: String) {
        try {
            cloud.reference.child(path).putFile(image)
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getFile(path: String): Uri {
        return try {
            cloud.reference.child(path).downloadUrl.await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }
}

