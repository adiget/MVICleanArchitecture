package com.example.mvi_clean_architecture.data.datasource.network

import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.PullRequestEntity
import com.example.mvi_clean_architecture.data.model.PullRequestGetBody
import com.example.mvi_clean_architecture.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GitRemote {
    fun getUsers(): Flow<List<GithubUser>>

    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(pullRequestGetBody: PullRequestGetBody): Flow<List<PullRequestEntity>>
}