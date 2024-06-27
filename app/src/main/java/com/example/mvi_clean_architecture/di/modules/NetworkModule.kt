package com.example.mvi_clean_architecture.di.modules

import com.example.mvi_clean_architecture.network.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return GithubService.create()
    }
}
