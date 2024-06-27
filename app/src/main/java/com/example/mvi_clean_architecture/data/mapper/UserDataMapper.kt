package com.example.mvi_clean_architecture.data.mapper

import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.domain.model.DomainUserData
import javax.inject.Inject

class UserDataMapper @Inject constructor() : DataMapper<GithubUser, DomainUserData> {

    override fun mapFromEntity(type: GithubUser): DomainUserData =
        DomainUserData(
            userId = type.login,
            avatarUrl = type.avatarUrl,
            htmlUrl = type.htmlUrl
        )
}