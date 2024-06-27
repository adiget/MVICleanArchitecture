package com.example.mvi_clean_architecture.presentation.ui.users.mapper

import com.example.mvi_clean_architecture.presentation.ui.users.model.UiUserData
import com.example.mvi_clean_architecture.domain.model.DomainUserData
import com.example.mvi_clean_architecture.presentation.ui.common.ViewMapper
import javax.inject.Inject

class UserViewMapper @Inject constructor() :
    ViewMapper<UiUserData, DomainUserData> {

    override fun mapToView(type: DomainUserData) =
        UiUserData(
            userId = type.userId,
            avatarUrl = type.avatarUrl,
            htmlUrl = type.htmlUrl
        )
    }