package com.example.mvi_clean_architecture.data.datasource

import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.PullRequestEntity
import com.example.mvi_clean_architecture.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GithubAppDataSource {
    fun getUsers(): Flow<List<GithubUser>>

    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(
        username: String,
        repoName: String,
        state: String
    ): Flow<List<PullRequestEntity>>
}