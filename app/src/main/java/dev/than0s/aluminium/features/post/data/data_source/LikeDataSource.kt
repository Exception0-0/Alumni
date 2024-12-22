package dev.than0s.aluminium.features.post.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dev.than0s.aluminium.core.data.remote.LIKES
import dev.than0s.aluminium.core.data.remote.POSTS
import dev.than0s.aluminium.core.data.remote.USER_ID
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.domain.data_class.Like
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LikeDataSource {
    suspend fun addLike(like: Like)
    suspend fun removeLike(like: Like)
    suspend fun getCurrentUserLikeStatus(postId: String): Like?
}

class LikeDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
) : LikeDataSource {

    override suspend fun addLike(like: Like) {
        try {
            store.collection(POSTS)
                .document(like.postId)
                .collection(LIKES)
                .document(like.userId)
                .set(like.toRemoteLike())
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeLike(like: Like) {
        try {
            store.collection(POSTS)
                .document(like.postId)
                .collection(LIKES)
                .document(like.userId)
                .delete()
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getCurrentUserLikeStatus(postId: String): Like? {
        return try {
            store.collection(POSTS)
                .document(postId)
                .collection(LIKES)
                .whereEqualTo(USER_ID, auth.currentUser!!.uid)
                .get()
                .await()
                .toObjects(RemoteLike::class.java)[0]
                .toLike(postId)
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}