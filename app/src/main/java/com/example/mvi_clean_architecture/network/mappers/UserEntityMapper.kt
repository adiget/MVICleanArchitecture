package com.example.mvi_clean_architecture.network.mappers

import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.UserEntity
import javax.inject.Inject

class UserEntityMapper @Inject constructor() : EntityMapper<GithubUser, UserEntity> {

    override fun mapFromModel(model: GithubUser): UserEntity {
        return UserEntity(
            userName = model.login?:"",
            profilePic = model.avatarUrl?:""
        )
    }
}