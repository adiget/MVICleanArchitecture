package com.example.mvi_clean_architecture.data.model

data class PullRequestEntity(
    var id: Int = -1,
    var desc : String = "",
    var title: String = "",
    var user: UserEntity = UserEntity(),
    var closedAt: String = "",
    var createdAt: String = ""
)