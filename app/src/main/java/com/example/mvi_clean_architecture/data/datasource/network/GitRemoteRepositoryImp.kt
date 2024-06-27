package com.example.mvi_clean_architecture.data.datasource.network

import com.example.mvi_clean_architecture.network.model.GithubPullRequest
import com.example.mvi_clean_architecture.common.AppDispatchers
import com.example.mvi_clean_architecture.common.Dispatcher
import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.PullRequestEntity
import com.example.mvi_clean_architecture.data.model.PullRequestGetBody
import com.example.mvi_clean_architecture.data.model.SingleRepoEntity
import com.example.mvi_clean_architecture.network.GithubService
import com.example.mvi_clean_architecture.network.mappers.PullRequestEntityMapper
import com.example.mvi_clean_architecture.network.mappers.SingleRepoEntityMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRemoteRepositoryImp @Inject constructor(
    private val githubService: GithubService,
    private val singleRepoEntityMapper: SingleRepoEntityMapper,
    private val pullRequestEntityMapper: PullRequestEntityMapper,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GitRemote {

    override fun getUsers(): Flow<List<GithubUser>> = flow {
        emit(githubService.getUsers())
    }.flowOn(ioDispatcher)

    override fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>> = flow {
        emit(githubService.getUserRepositories(username)
            .map { singleRepoEntityMapper.mapFromModel(it) })
    }.flowOn(ioDispatcher)

    override fun getPullRequestList(pullRequestGetBody: PullRequestGetBody): Flow<List<PullRequestEntity>> = flow {
        val pullRequestLists: List<GithubPullRequest> = githubService.getPullRequestForGithubRepo(
            pullRequestGetBody.userName,
            pullRequestGetBody.repositoryName,
            pullRequestGetBody.state
        )

        val pullRequestEntities: List<PullRequestEntity> = pullRequestLists.map {
            pullRequestEntityMapper.mapFromModel(it)
        }

        emit(pullRequestEntities)
    }.flowOn(ioDispatcher)
}