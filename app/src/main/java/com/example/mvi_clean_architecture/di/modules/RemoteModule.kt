package com.example.mvi_clean_architecture.di.modules

import com.example.mvi_clean_architecture.data.datasource.network.GitRemote
import com.example.mvi_clean_architecture.data.datasource.network.GitRemoteRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @Binds
    fun bindsGitRemote(
        gitRemoteImp: GitRemoteRepositoryImp
    ): GitRemote
}