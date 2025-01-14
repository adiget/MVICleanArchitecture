package com.example.mvi_clean_architecture.domain

import com.example.mvi_clean_architecture.domain.model.DomainRepoData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val gitRepository: GithubRepository
) {
    operator fun invoke(requestValues: Params): Flow<List<DomainRepoData>> =
        gitRepository.getUserGitRepositories(requestValues.userId)

    data class Params(
        val userId: String
    )
}