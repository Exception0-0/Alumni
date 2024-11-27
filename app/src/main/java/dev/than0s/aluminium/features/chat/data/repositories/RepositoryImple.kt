package dev.than0s.aluminium.features.chat.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.chat.data.data_source.DataSource
import dev.than0s.aluminium.features.chat.domain.data_class.Chat
import dev.than0s.aluminium.features.chat.domain.data_class.User
import dev.than0s.aluminium.features.chat.domain.repository.Repository
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImple @Inject constructor(
    private val dataSource: DataSource
) : Repository {
    override suspend fun addChat(chat: Chat): Either<Failure, Unit> {
        return try {
            dataSource.addChat(chat)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun removeChat(chat: Chat): Either<Failure, Unit> {
        return try {
            dataSource.removeChat(chat)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getReceiverChatFlow(receiverId: String): Either<Failure, Flow<List<Chat>>> {
        return try {
            Either.Right(dataSource.getReceiverChatFlow(receiverId))
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getSenderChatFlow(receiverId: String): Either<Failure, Flow<List<Chat>>> {
        return try {
            Either.Right(dataSource.getSenderChatFlow(receiverId))
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getCurrentChatList(): Either<Failure, List<User>> {
        return try {
            Either.Right(dataSource.getCurrentChatList())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getUserProfile(userId: String): Either<Failure, User> {
        return try {
            Either.Right(dataSource.getUserProfile(userId))
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}