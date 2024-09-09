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
import dev.than0s.aluminium.features.settings.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.auth.data.data_source.EmailAuthDataSource
import dev.than0s.aluminium.features.settings.data.data_source.FirebaseAccountDataSourceImple
import dev.than0s.aluminium.features.auth.data.data_source.FirebaseEmailAuthDataSourceImple
import dev.than0s.aluminium.features.settings.data.repositories.AccountRepositoryImple
import dev.than0s.aluminium.features.auth.data.repositories.EmailAuthRepositoryImple
import dev.than0s.aluminium.features.settings.domain.repository.AccountRepository
import dev.than0s.aluminium.features.auth.domain.repository.EmailAuthRepository
import dev.than0s.aluminium.features.settings.domain.use_cases.AccountSignOutUseCase
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignInUseCase
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignUpUseCase
import dev.than0s.aluminium.features.register.data.data_source.FirebaseRegisterDataSourceImple
import dev.than0s.aluminium.features.register.data.data_source.RegisterDataSource
import dev.than0s.aluminium.features.register.data.repositories.RegistrationRepositoryImple
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import dev.than0s.aluminium.features.register.domain.use_cases.RegistrationUseCase
import dev.than0s.aluminium.features.register.domain.use_cases.RequestsListUseCase
import dev.than0s.aluminium.features.settings.data.data_source.FirebaseProfileDataSourceImple
import dev.than0s.aluminium.features.settings.data.data_source.ProfileDataSource
import dev.than0s.aluminium.features.settings.data.repositories.ProfileRepositoryImple
import dev.than0s.aluminium.features.settings.domain.repository.ProfileRepository
import dev.than0s.aluminium.features.settings.domain.use_cases.ProfileCurrentUserUseCase
import dev.than0s.aluminium.features.settings.domain.use_cases.ProfileUpdateProfileUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindAccountRepository(impl: AccountRepositoryImple): AccountRepository

    @Binds
    abstract fun bindAccountDataSource(impl: FirebaseAccountDataSourceImple): AccountDataSource

    @Binds
    abstract fun bindEmailAuthRepository(impl: EmailAuthRepositoryImple): EmailAuthRepository

    @Binds
    abstract fun bindEmailAuthDataSource(impl: FirebaseEmailAuthDataSourceImple): EmailAuthDataSource

    @Binds
    abstract fun bindRegistrationRepository(impl: RegistrationRepositoryImple): RegistrationRepository

    @Binds
    abstract fun bindRegisterDataSource(impl: FirebaseRegisterDataSourceImple): RegisterDataSource

    @Binds
    abstract fun bindProfileDataSource(impl: FirebaseProfileDataSourceImple): ProfileDataSource

    @Binds
    abstract fun bindProfileRepository(imple: ProfileRepositoryImple): ProfileRepository
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

@InstallIn(SingletonComponent::class)
@Module
object UseCases {
    @Provides
    fun signInUseCase(repository: EmailAuthRepository) = EmailSignInUseCase(repository)

    @Provides
    fun signOutUseCase(repository: AccountRepository) = AccountSignOutUseCase(repository)

    @Provides
    fun signUpUseCase(repository: EmailAuthRepository) = EmailSignUpUseCase(repository)

    @Provides
    fun registerUseCase(repository: RegistrationRepository) = RegistrationUseCase(repository)

    @Provides
    fun requestsListUseCase(repository: RegistrationRepository) = RequestsListUseCase(repository)

    @Provides
    fun updateProfileUseCase(repository: ProfileRepository) =
        ProfileUpdateProfileUseCase(repository)

    @Provides
    fun currentUserUseCase(repository: ProfileRepository) = ProfileCurrentUserUseCase(repository)
}