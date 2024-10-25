package dev.than0s.aluminium.features.chat.data.data_source

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.storage.FirebaseStorage
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface DataSource {
    suspend fun addChat(chat: Chat)
    suspend fun removeChat(chat: Chat)
    suspend fun getReceiverChatFlow(receiverId: String): Flow<List<Chat>>
    suspend fun getSenderChatFlow(receiverId: String): Flow<List<Chat>>
    suspend fun getUserProfile(userId: String): User
    suspend fun getCurrentChatList(): List<User>
}

class DataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val cloud: FirebaseStorage,
) : DataSource {
    override suspend fun addChat(chat: Chat) {
        try {
            store.collection(CHATS)
                .document(chat.receiverId)
                .collection(chat.senderId)
                .document(chat.id)
                .set(chat)
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeChat(chat: Chat) {
        try {
            store.collection(CHATS)
                .document(chat.receiverId)
                .collection(chat.senderId)
                .document(chat.id)
                .delete()
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getReceiverChatFlow(receiverId: String): Flow<List<Chat>> {
        return try {
            store.collection(CHATS)
                .document(receiverId)
                .collection(auth.currentUser!!.uid)
                .dataObjects<Chat>()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getSenderChatFlow(receiverId: String): Flow<List<Chat>> {
        return try {
            store.collection(CHATS)
                .document(auth.currentUser!!.uid)
                .collection(receiverId)
                .dataObjects<Chat>()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getCurrentChatList(): List<User> {
        try {
            val result = mutableListOf<User>()
            val chatList = store.collection(CHATS)
                .document(auth.currentUser!!.uid)
                .get()
                .await()
                .data
                ?.keys
            chatList?.let {
                for (userId in it) {
                    val profile = getUserProfile(userId)
                    val image = getProfileImage(userId)
                    result.add(
                        User(
                            firstName = profile?.firstName.toString(),
                            lastName = profile?.lastName.toString(),
                            profileImage = image
                        )
                    )
                }
            }
            return result
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getUserProfile(userId: String): User {
        return try {
            store.collection(PROFILE)
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)!!
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun getProfileImage(userId: String): Uri {
        return try {
            cloud.reference.child("$PROFILE_IMAGE/${userId}/0").downloadUrl.await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        private const val CHATS = "chats"
        private const val PROFILE_IMAGE = "profile_images"
        private const val PROFILE = "profile"
    }
}