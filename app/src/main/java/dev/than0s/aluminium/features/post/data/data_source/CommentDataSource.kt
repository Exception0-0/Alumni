package dev.than0s.aluminium.features.post.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObjects
import dev.than0s.aluminium.core.data.remote.COMMENTS
import dev.than0s.aluminium.core.data.remote.POSTS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CommentDataSource {
    suspend fun getComments(postId: String): List<Comment>
    suspend fun addComment(comment: Comment)
    suspend fun removeComment(comment: Comment)
}

class CommentDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
) : CommentDataSource {

    override suspend fun addComment(comment: Comment) {
        try {
            store.collection(POSTS)
                .document(comment.postId)
                .collection(COMMENTS)
                .document(comment.id)
                .set(comment)
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeComment(comment: Comment) {
        try {
            store.collection(POSTS)
                .document(comment.postId)
                .collection(COMMENTS)
                .document(comment.id)
                .delete()
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return try {
            store.collection(POSTS)
                .document(postId)
                .collection(COMMENTS)
                .get()
                .await()
                .toObjects()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}