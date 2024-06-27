package com.example.mvi_clean_architecture.data.repository

import com.example.mvi_clean_architecture.common.AppDispatchers
import com.example.mvi_clean_architecture.common.Dispatcher
import com.example.mvi_clean_architecture.data.datasource.GitDataSourceFactory
import com.example.mvi_clean_architecture.data.datasource.GithubAppDataSource
import com.example.mvi_clean_architecture.data.mapper.PrDataMapper
import com.example.mvi_clean_architecture.data.mapper.RepoDataMapper
import com.example.mvi_clean_architecture.data.mapper.UserDataMapper
import com.example.mvi_clean_architecture.data.model.PullRequestEntity
import com.example.mvi_clean_architecture.domain.GithubRepository
import com.example.mvi_clean_architecture.domain.model.DomainPrData
import com.example.mvi_clean_architecture.domain.model.DomainRepoData
import com.example.mvi_clean_architecture.domain.model.DomainUserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val singleRepoMapper: RepoDataMapper,
    private val pullRequestMapper: PrDataMapper,
    private val userMapper: UserDataMapper,
    private val gitDataSourceFactory: GitDataSourceFactory,
    @Dispatcher(AppDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) : GithubRepository {

    override fun getGitHubUsers(): Flow<List<DomainUserData>> =
        gitDataSourceFactory.getRemoteDataSource().getUsers()
            .map { it ->
                it.map { userMapper.mapFromEntity(it)}
            }
            .flowOn(defaultDispatcher)

    override fun getUserGitRepositories(username: String): Flow<List<DomainRepoData>> =
        gitDataSourceFactory.getRemoteDataSource()
            .getUserGitRepositories(username).map { it ->
                it.map { singleRepoMapper.mapFromEntity(it)}
            }.flowOn(defaultDispatcher)

     override fun getPullRequestList(
         username: String,
         repoName: String,
         state: DomainPrData.State
     ): Flow<List<DomainPrData>> {
        val gitDataSource: GithubAppDataSource = gitDataSourceFactory.getRemoteDataSource()

         val pullRequestEntityListFlow : Flow<List<PullRequestEntity>> = gitDataSource.getPullRequestList(
             username,
             repoName,
             getPullRequestState(state)
         )

         val pullRequestListFlow: Flow<List<DomainPrData>> = pullRequestEntityListFlow.map {
             it -> it.map { pullRequestMapper.mapFromEntity(it) }
         }
         
         return pullRequestListFlow.flowOn(defaultDispatcher)
    }

    private fun getPullRequestState(state: DomainPrData.State): String {
        return when (state) {
            DomainPrData.State.OPEN -> "open"
            DomainPrData.State.CLOSED -> "closed"
            else -> "all"
        }
    }
}