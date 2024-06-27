package com.example.mvi_clean_architecture.presentation.ui.repos.mapper

import com.example.mvi_clean_architecture.domain.model.DomainRepoData
import com.example.mvi_clean_architecture.presentation.ui.common.ViewMapper
import com.example.mvi_clean_architecture.presentation.ui.repos.model.UiRepoData
import javax.inject.Inject

class RepoViewMapper @Inject constructor() :
    ViewMapper<UiRepoData, DomainRepoData> {

    override fun mapToView(type: DomainRepoData) =
        UiRepoData(
            repoName = type.repoName,
            repoDescription = type.repoDescription,
            openIssuesCount = type.openIssuesCount,
            forksCount = type.forksCount,
            defaultBranch = type.defaultBranch
        )
    }