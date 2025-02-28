package dev.than0s.aluminium.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.than0s.aluminium.features.auth.data.remote.AuthRemote
import dev.than0s.aluminium.features.auth.data.remote.AuthRemoteImple
import dev.than0s.aluminium.features.auth.data.repository.AuthRepositoryImple
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.features.chat.data.remote.RemoteChat
import dev.than0s.aluminium.features.chat.data.remote.RemoteChatImple
import dev.than0s.aluminium.features.chat.data.repository.RepositoryChatImple
import dev.than0s.aluminium.features.chat.domain.repository.RepositoryChat
import dev.than0s.aluminium.features.last_seen.data.remote.RemoteUserStatus
import dev.than0s.aluminium.features.last_seen.data.remote.RemoteUserStatusImple
import dev.than0s.aluminium.features.last_seen.data.repository.RepositoryLastSeenImple
import dev.than0s.aluminium.features.last_seen.domain.repository.RepositoryLastSeen
import dev.than0s.aluminium.features.notification.data.remote.RemoteMessaging
import dev.than0s.aluminium.features.notification.data.remote.RemoteMessagingImple
import dev.than0s.aluminium.features.notification.data.repository.RepositoryMessagingImple
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import dev.than0s.aluminium.features.post.data.remote.CommentRemote
import dev.than0s.aluminium.features.post.data.remote.CommentRemoteImple
import dev.than0s.aluminium.features.post.data.remote.LikeDataSourceImple
import dev.than0s.aluminium.features.post.data.remote.LikeRemote
import dev.than0s.aluminium.features.post.data.remote.PostRemote
import dev.than0s.aluminium.features.post.data.remote.PostRemoteImple
import dev.than0s.aluminium.features.post.data.repositories.CommentRepositoryImple
import dev.than0s.aluminium.features.post.data.repositories.LikeRepositoryImple
import dev.than0s.aluminium.features.post.data.repositories.PostRepositoryImple
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import dev.than0s.aluminium.features.profile.data.remote.ContactRemote
import dev.than0s.aluminium.features.profile.data.remote.ContactRemoteImple
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSource
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSourceImple
import dev.than0s.aluminium.features.profile.data.repositories.ContactRepositoryImple
import dev.than0s.aluminium.features.profile.data.repositories.ProfileRepositoryImple
import dev.than0s.aluminium.features.profile.domain.repository.ContactRepository
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSource
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSourceImple
import dev.than0s.aluminium.features.registration.data.repositories.RegistrationRepositoryImple
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSourceImple
import dev.than0s.aluminium.features.splash.data.repositories.AccountRepositoryImple
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    // auth
    @Binds
    abstract fun bindAuthDataSource(imple: AuthRemoteImple): AuthRemote

    @Binds
    abstract fun bindAuthRepository(imple: AuthRepositoryImple): AuthRepository

    // post
    @Binds
    abstract fun bindPostDataSource(imple: PostRemoteImple): PostRemote

    @Binds
    abstract fun bindPostRepository(imple: PostRepositoryImple): PostRepository

    @Binds
    abstract fun bindLikeDataSource(imple: LikeDataSourceImple): LikeRemote

    @Binds
    abstract fun bindLikeRepository(imple: LikeRepositoryImple): LikeRepository

    @Binds
    abstract fun bindCommentDataSource(imple: CommentRemoteImple): CommentRemote

    @Binds
    abstract fun bindCommentRepository(imple: CommentRepositoryImple): CommentRepository

    // profile
    @Binds
    abstract fun bindProfileDataSource(imple: ProfileDataSourceImple): ProfileDataSource

    @Binds
    abstract fun bindProfileRepository(imple: ProfileRepositoryImple): ProfileRepository

    @Binds
    abstract fun bindContactRemote(imple: ContactRemoteImple): ContactRemote

    @Binds
    abstract fun bindContactRepository(imple: ContactRepositoryImple): ContactRepository

    // registration
    @Binds
    abstract fun bindRegisterDataSource(imple: RegisterDataSourceImple): RegisterDataSource

    @Binds
    abstract fun bindRegistrationRepository(imple: RegistrationRepositoryImple): RegistrationRepository

    // splash
    @Binds
    abstract fun bindAccountDataSource(imple: AccountDataSourceImple): AccountDataSource

    @Binds
    abstract fun bindAccountRepository(imple: AccountRepositoryImple): AccountRepository

    // chat
    @Binds
    abstract fun bindRemoteChat(imple: RemoteChatImple): RemoteChat

    @Binds
    abstract fun bindRepositoryChat(imple: RepositoryChatImple): RepositoryChat

    // last seen
    @Binds
    abstract fun bindRemoteLastSeen(imple: RemoteUserStatusImple): RemoteUserStatus

    @Binds
    abstract fun bindRepositoryLastSeen(imple: RepositoryLastSeenImple): RepositoryLastSeen

    // notification
    @Binds
    abstract fun bindRemoteMessaging(imple: RemoteMessagingImple): RemoteMessaging

    @Binds
    abstract fun bindRepositoryMessaging(imple: RepositoryMessagingImple): RepositoryMessaging
}

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    fun store(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun storage(): FirebaseStorage = Firebase.storage

    @Provides
    fun database(): FirebaseDatabase = Firebase.database

    @Provides
    fun messaging(): FirebaseMessaging = Firebase.messaging
}