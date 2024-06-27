package com.example.mvi_clean_architecture.data.datasource

import javax.inject.Inject

class GitDataSourceFactory @Inject constructor(
    private val githubAppRemoteDataSource: GithubAppNetworkDataSource
) {

    fun getRemoteDataSource(): GithubAppDataSource {
        return githubAppRemoteDataSource
    }
}