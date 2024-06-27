package com.example.mvi_clean_architecture.data.model

data class PullRequestGetBody(
    var userName : String,
    var repositoryName : String,
    var state : String
)