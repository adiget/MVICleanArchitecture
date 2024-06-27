package com.example.mvi_clean_architecture.network.mappers

import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.PullRequestEntity
import com.example.mvi_clean_architecture.network.model.GithubPullRequest
import javax.inject.Inject

class PullRequestEntityMapper @Inject constructor(private val userEntityMapper: UserEntityMapper) :
    EntityMapper<GithubPullRequest, PullRequestEntity> {

    override fun mapFromModel(model: GithubPullRequest): PullRequestEntity {
        return PullRequestEntity(
            id = model.id?:-1,
            desc = model.body?:"No description ",
            user = userEntityMapper.mapFromModel(model.user ?: GithubUser()),
            title = model.title ?:"No title",
            createdAt = model.createdAt ?:"",
            closedAt = model.closedAt ?:""
        )
    }
}