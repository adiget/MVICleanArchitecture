package com.example.mvi_clean_architecture.presentation.mapper

import com.example.mvi_clean_architecture.domain.model.User
import com.example.mvi_clean_architecture.presentation.ui.common.ViewMapper
import com.example.mvi_clean_architecture.presentation.views.UserView
import javax.inject.Inject

class UserViewMapper @Inject constructor() : ViewMapper<UserView, User> {
    override fun mapToView(type: User): UserView {
        return UserView(
            userName = type.userName,
            profilePic = type.profilePic
        )
    }
}