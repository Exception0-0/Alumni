package dev.than0s.aluminium.features.post.data.data_source

import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LikeDataSource {
    suspend fun addLike(postId: String)
    suspend fun removeLike(postId: String)
    suspend fun hasUserLiked(postId: String): Boolean
}

class LikeDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
) : LikeDataSource {

    override suspend fun addLike(postId: String) {
        try {
            store.collection(POSTS)
                .document(postId)
                .collection(LIKES)
                .document(auth.currentUser!!.uid)
                .set(
                    hashMapOf(
                        "timeStamp" to Timestamp.now()
                    )
                )
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeLike(postId: String) {
        try {
            store.collection(POSTS)
                .document(postId)
                .collection(LIKES)
                .document(auth.currentUser!!.uid)
                .delete()
                .await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getLikeCount(postId: String): Int {
        return try {
            store.collection(POSTS)
                .document(postId)
                .collection(LIKES)
                .count()
                .get(AggregateSource.SERVER)
                .await().count.toInt()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun hasUserLiked(postId: String): Boolean {
        return try {
            store.collection(POSTS)
                .document(postId)
                .collection(LIKES)
                .document(auth.currentUser!!.uid)
                .get()
                .await()
                .exists()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }
}