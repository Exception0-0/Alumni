package dev.than0s.aluminium.features.post.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.post.data.mapper.RawPost
import dev.than0s.aluminium.features.post.data.mapper.toPost
import dev.than0s.aluminium.features.post.data.mapper.toRawPost
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostDataSource {
    suspend fun addPost(post: Post)
    suspend fun deletePost(id: String)
    suspend fun getPostFlow(id: String?): Flow<List<Post>>
    suspend fun addLike(id: String)
    suspend fun removeLike(id: String)
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

    override suspend fun deletePost(id: String) {
        try {
            store.collection(POSTS).document(id).delete().await()
            cloud.reference.child("$POSTS/$id/0").delete().await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getPostFlow(id: String?): Flow<List<Post>> {
        return try {
            if (id != null) {
                store.collection(POSTS).whereEqualTo("userId", id).dataObjects<RawPost>()
                    .toPost(::getUser, ::getFile, ::hasUserLiked)
            } else {
                store.collection(POSTS).dataObjects<RawPost>()
                    .toPost(::getUser, ::getFile, ::hasUserLiked)
            }
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun addLike(id: String) {
        try {
            store.collection(POSTS)
                .document(id)
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

    override suspend fun removeLike(id: String) {
        try {
            store.collection(POSTS)
                .document(id)
                .collection(LIKES)
                .document(auth.currentUser!!.uid)
                .delete()
                .await()
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
    }
}