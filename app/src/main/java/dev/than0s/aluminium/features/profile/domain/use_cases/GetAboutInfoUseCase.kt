package dev.than0s.aluminium.features.profile.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAboutInfoUseCase @Inject constructor(
    private val repository: ProfileRepository
){
    suspend operator fun invoke(userId: String): Resource<AboutInfo> {
        return repository.getAboutInfo(userId)
    }
}