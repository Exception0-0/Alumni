package dev.than0s.aluminium.features.post.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.core.data.remote.POSTS
import dev.than0s.aluminium.core.data.remote.USER_ID
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.post.data.mapper.RemotePost
import dev.than0s.aluminium.features.post.data.mapper.toPost
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.features.post.data.mapper.toRemotePost
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PostRemote {
    suspend fun getPosts(userId: String?): List<Post>
    suspend fun addPost(post: Post)
    suspend fun deletePost(postId: String)
}

class PostRemoteImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloud: FirebaseStorage,
) :
    PostRemote {

    override suspend fun getPosts(userId: String?): List<Post> {
        return try {
            if (userId != null) {
                store.collection(POSTS)
                    .whereEqualTo(USER_ID, userId)
                    .get()
                    .await()
                    .toObjects<RemotePost>()
                    .map { it.toPost() }
            } else {
                store.collection(POSTS)
                    .get()
                    .await()
                    .toObjects<RemotePost>()
                    .map { it.toPost() }
            }
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun addPost(post: Post) {
        try {
            cloud.reference
                .child("$POSTS/${post.id}/0")
                .putFile(post.file)
                .await()
                .storage
                .downloadUrl
                .await()
                .let {
                    store.collection(POSTS)
                        .document(post.id)
                        .set(post.copy(file = it).toRemotePost())
                        .await()
                }
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun deletePost(postId: String) {
        try {
            store.collection(POSTS).document(postId).delete().await()
            cloud.reference.child("$POSTS/${postId}/0").delete().await()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}