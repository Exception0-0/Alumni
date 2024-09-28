package dev.than0s.aluminium.features.post.data.data_source

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.post.data.mapper.RawComment
import dev.than0s.aluminium.features.post.data.mapper.RawPost
import dev.than0s.aluminium.features.post.data.mapper.toComment
import dev.than0s.aluminium.features.post.data.mapper.toPost
import dev.than0s.aluminium.features.post.data.mapper.toRawComment
import dev.than0s.aluminium.features.post.data.mapper.toRawPost
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostDataSource {
    suspend fun addPost(post: Post)
    suspend fun deletePost(postId: String)
    suspend fun getPostFlow(userId: String?): Flow<List<Post>>
    suspend fun addLike(postId: String)
    suspend fun removeLike(postId: String)
    suspend fun addComment(comment: Comment)
    suspend fun removeComment(postId: String, commentId: String)
    suspend fun getCommentFlow(postId: String): Flow<List<Comment>>
}

class PostDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
    private val auth: FirebaseAuth
) :
    PostDataSource {

    override suspend fun addPost(post: Post) {
        try {
            store.collection(POSTS).document(post.id).set(post.toRawPost()).await()
            cloud.reference.child("$POSTS/${post.id}/0").putFile(post.file).await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun deletePost(postId: String) {
        try {
            store.collection(POSTS).document(postId).delete().await()
            cloud.reference.child("$POSTS/$postId/0").delete().await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getPostFlow(userId: String?): Flow<List<Post>> {
        return try {
            if (userId != null) {
                store.collection(POSTS).whereEqualTo("userId", userId).dataObjects<RawPost>()
                    .toPost(::getUser, ::getFile, ::hasUserLiked)
            } else {
                store.collection(POSTS).dataObjects<RawPost>()
                    .toPost(::getUser, ::getFile, ::hasUserLiked)
            }
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

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
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun addComment(comment: Comment) {
        try {
            store.collection(POSTS)
                .document(comment.postId)
                .collection(COMMENTS)
                .document(comment.id)
                .set(comment.toRawComment())
                .await()
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getCommentFlow(postId: String): Flow<List<Comment>> {
        return try {
            store.collection(POSTS)
                .document(postId)
                .collection(COMMENTS)
                .dataObjects<RawComment>()
                .toComment(postId)
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun hasUserLiked(id: String): Boolean {
        return try {
            // just checking is document present or not
            // if present user already like it
            store.collection(POSTS)
                .document(id)
                .collection(LIKES)
                .document(auth.currentUser!!.uid)
                .get()
                .await()
                .data != null
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getUser(id: String): User {
        return try {
            val doc = store.collection(PROFILE).document(id).get().await()
            val profile = cloud.reference.child("$PROFILE_IMAGE/$id/0").downloadUrl.await()
            User(
                userId = doc.id,
                firstName = doc.get("firstName").toString(),
                lastName = doc.get("lastName").toString(),
                profileImage = profile
            )
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getFile(id: String): Uri {
        return try {
            cloud.reference.child("$POSTS/$id/0").downloadUrl.await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }


    private suspend fun getLikeCount(id: String): Int {
        return try {
            store.collection(POSTS)
                .document(id)
                .collection(LIKES)
                .count()
                .get(AggregateSource.SERVER)
                .await().count.toInt()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val POSTS = "posts"
        private const val PROFILE = "profile"
        private const val PROFILE_IMAGE = "profile_images"
        private const val LIKES = "likes"
        private const val COMMENTS = "comments"
    }
}