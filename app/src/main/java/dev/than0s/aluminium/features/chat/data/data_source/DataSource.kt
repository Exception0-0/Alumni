package dev.than0s.aluminium.features.chat.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.ChatListItem
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface DataSource {
    suspend fun addChat(chat: Chat)
    suspend fun removeChat(chat: Chat)
    suspend fun getChatFlow(receiverId: String): Flow<List<Chat>>
//    suspend fun getCurrentChatList(): List<ChatListItem>
}

class DataSourceImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth
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

    override suspend fun getChatFlow(receiverId: String): Flow<List<Chat>> {
        return try {
            flow {
                store.collection(CHATS)
                    .document(receiverId)
                    .collection(auth.currentUser!!.uid)
                    .dataObjects<Chat>()
                    .collect(this)

                store.collection(CHATS)
                    .document(auth.currentUser!!.uid)
                    .collection(receiverId)
                    .dataObjects<Chat>()
                    .collect(this)
            }
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

//    override suspend fun getCurrentChatList(): List<ChatListItem> {
//        return try {
//            store.collection(CHATS)
//                .document(auth.currentUser!!.uid)
//
//        }
//    }

    companion object {
        private val CHATS = "chats"
    }
}