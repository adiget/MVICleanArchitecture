package com.example.mvi_clean_architecture.domain.model

data class DomainRepoData (
    var repoName : String = "",
    var repoDescription : String = "",
    var openIssuesCount : Int = -1,
    var forksCount : Int = -1,
    var defaultBranch : String = ""
)