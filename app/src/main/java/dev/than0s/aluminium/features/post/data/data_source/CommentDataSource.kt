package dev.than0s.aluminium.features.post.data.data_source

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CommentDataSource {
    suspend fun getComments(postId: String): List<Comment>
    suspend fun addComment(comment: Comment)
    suspend fun removeComment(postId: String, commentId: String)
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
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeComment(postId: String, commentId: String) {
        try {
            store.collection(POSTS)
                .document(postId)
                .collection(COMMENTS)
                .document(commentId)
                .delete()
                .await()
        } catch (e: FirebaseException) {
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
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}