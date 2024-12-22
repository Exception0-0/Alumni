package dev.than0s.aluminium.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.than0s.aluminium.features.auth.data.remote.AuthDataSource
import dev.than0s.aluminium.features.auth.data.remote.AuthDataSourceImple
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.data.repositories.AccountRepositoryImple
import dev.than0s.aluminium.features.auth.data.repository.AuthRepositoryImple
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.features.post.data.data_source.CommentDataSource
import dev.than0s.aluminium.features.post.data.data_source.CommentDataSourceImple
import dev.than0s.aluminium.features.post.data.data_source.LikeDataSource
import dev.than0s.aluminium.features.post.data.data_source.LikeDataSourceImple
import dev.than0s.aluminium.features.post.data.data_source.PostDataSource
import dev.than0s.aluminium.features.post.data.data_source.PostDataSourceImple
import dev.than0s.aluminium.features.post.data.repositories.CommentRepositoryImple
import dev.than0s.aluminium.features.post.data.repositories.LikeRepositoryImple
import dev.than0s.aluminium.features.post.data.repositories.PostRepositoryImple
import dev.than0s.aluminium.features.post.domain.repository.CommentRepository
import dev.than0s.aluminium.features.post.domain.repository.LikeRepository
import dev.than0s.aluminium.features.post.domain.repository.PostRepository
import dev.than0s.aluminium.features.profile.data.remote.ContactRemote
import dev.than0s.aluminium.features.profile.data.remote.ContactRemoteImple
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSource
import dev.than0s.aluminium.features.registration.data.repositories.RegistrationRepositoryImple
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSource
import dev.than0s.aluminium.features.profile.data.remote.ProfileDataSourceImple
import dev.than0s.aluminium.features.profile.data.repositories.ContactRepositoryImple
import dev.than0s.aluminium.features.profile.data.repositories.ProfileRepositoryImple
import dev.than0s.aluminium.features.profile.domain.repository.ContactRepository
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import dev.than0s.aluminium.features.registration.data.remote.RegisterDataSourceImple
import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSourceImple

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    // auth
    @Binds
    abstract fun bindAuthDataSource(imple: AuthDataSourceImple): AuthDataSource

    @Binds
    abstract fun bindAuthRepository(imple: AuthRepositoryImple): AuthRepository

    // post
    @Binds
    abstract fun bindPostDataSource(imple: PostDataSourceImple): PostDataSource

    @Binds
    abstract fun bindPostRepository(imple: PostRepositoryImple): PostRepository

    @Binds
    abstract fun bindLikeDataSource(imple: LikeDataSourceImple): LikeDataSource

    @Binds
    abstract fun bindLikeRepository(imple: LikeRepositoryImple): LikeRepository

    @Binds
    abstract fun bindCommentDataSource(imple: CommentDataSourceImple): CommentDataSource

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
}