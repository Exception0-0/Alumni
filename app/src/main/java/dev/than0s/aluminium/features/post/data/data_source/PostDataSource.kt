package dev.than0s.aluminium.features.post.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.post.data.mapper.RawPost
import dev.than0s.aluminium.features.post.data.mapper.toPost
import dev.than0s.aluminium.features.post.data.mapper.toRawPost
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostDataSource {
    suspend fun getPosts(): List<Post>
    suspend fun getCurrentUserPosts(): List<Post>
    suspend fun addPost(post: Post)
    suspend fun deletePost(postId: String)
    suspend fun getUserProfile(userId: String): User
}

class PostDataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
    private val auth: FirebaseAuth
) :
    PostDataSource {

    override suspend fun getPosts(): List<Post> {
        return try {
            store.collection(POSTS)
                .get()
                .await()
                .toObjects<RawPost>()
                .toPost(::getFile)
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getCurrentUserPosts(): List<Post> {
        return try {
            store.collection(POSTS)
                .whereEqualTo("userId", auth.currentUser!!.uid)
                .get()
                .await()
                .toObjects<RawPost>()
                .toPost(::getFile)

        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun addPost(post: Post) {
        try {
            store.collection(POSTS).document(post.id).set(post.toRawPost()).await()
            cloud.reference.child("$POSTS/${post.id}/0").putFile(post.file).await()
        } catch (e: FirebaseException) {
            store.collection(POSTS).document(post.id).delete().await()
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun deletePost(postId: String) {
        try {
            store.collection(POSTS).document(postId).delete().await()
            cloud.reference.child("$POSTS/$postId/0").delete().await()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getUserProfile(userId: String): User {
        return try {
            val doc = store.collection(PROFILE).document(userId).get().await()
            val profile = cloud.reference.child("$PROFILE_IMAGE/$userId/0").downloadUrl.await()
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
}