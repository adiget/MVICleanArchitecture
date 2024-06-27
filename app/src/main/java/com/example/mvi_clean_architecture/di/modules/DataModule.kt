package com.example.mvi_clean_architecture.di.modules

import com.example.mvi_clean_architecture.data.repository.GithubRepositoryImpl
import com.example.mvi_clean_architecture.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsGitRepository(
        gitRepository: GithubRepositoryImpl
    ): GithubRepository
}
