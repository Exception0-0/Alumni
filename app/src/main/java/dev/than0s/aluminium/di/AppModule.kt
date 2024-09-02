package dev.than0s.aluminium.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.than0s.aluminium.features.auth.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.auth.data.data_source.EmailAuthDataSource
import dev.than0s.aluminium.features.auth.data.data_source.FirebaseAccountDataSourceImple
import dev.than0s.aluminium.features.auth.data.data_source.FirebaseEmailAuthDataSourceImple
import dev.than0s.aluminium.features.auth.data.repositories.AccountRepositoryImple
import dev.than0s.aluminium.features.auth.data.repositories.EmailAuthRepositoryImple
import dev.than0s.aluminium.features.auth.domain.repository.AccountRepository
import dev.than0s.aluminium.features.auth.domain.repository.EmailAuthRepository
import dev.than0s.aluminium.features.auth.domain.use_cases.AccountSignOutUseCase
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignInUseCase
import dev.than0s.aluminium.features.auth.domain.use_cases.EmailSignUpUseCase

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
}

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth
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
}