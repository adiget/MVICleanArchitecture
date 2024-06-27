package com.example.mvi_clean_architecture.presentation.ui.prs.model

import com.example.mvi_clean_architecture.presentation.views.UserView

data class UiPrData(
    var id : Int = -1,
    var prTitle: String = "",
    var prDesc: String = "",
    var user: UserView = UserView(),
    var closedAt: String = "",
    var createdAt: String = ""
)

fun buildUiPrDataPreview() = UiPrData(
    id = 1,
    prTitle = "pr title",
    prDesc = "pr desc",
    user = UserView(),
    closedAt = "",
    createdAt = ""
)
