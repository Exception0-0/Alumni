package dev.than0s.aluminium.features.splash.data.repositories

import dev.than0s.aluminium.features.splash.data.data_source.AccountDataSource
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImple @Inject constructor(private val dataSource: AccountDataSource) :
    AccountRepository {
    override val currentUserId get() = dataSource.currentUserId
}