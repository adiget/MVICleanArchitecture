package com.example.mvi_clean_architecture.presentation.ui.users

import com.example.mvi_clean_architecture.presentation.ui.base.ViewEvent
import com.example.mvi_clean_architecture.presentation.ui.base.ViewSideEffect
import com.example.mvi_clean_architecture.presentation.ui.base.ViewState
import com.example.mvi_clean_architecture.presentation.ui.users.model.UiUserData

class UsersContract {

    sealed class UsersViewEvent : ViewEvent {
        object Refresh : UsersViewEvent()
        object Retry : UsersViewEvent()
        data class UserSelection(val user: UiUserData) : UsersViewEvent()
    }

    sealed interface UsersUiState : ViewState {
        data class Success(val users: List<UiUserData>) : UsersUiState
        object Error : UsersUiState
        object Loading : UsersUiState
    }

    sealed class UsersEffect : ViewSideEffect {
        object DataWasLoaded : UsersEffect()

        sealed class Navigation : UsersEffect() {
            data class ToRepos(val userId: String): Navigation()
        }
    }
}
