package com.example.mvi_clean_architecture.presentation.ui.repos

import com.example.mvi_clean_architecture.presentation.ui.base.ViewEvent
import com.example.mvi_clean_architecture.presentation.ui.base.ViewSideEffect
import com.example.mvi_clean_architecture.presentation.ui.base.ViewState
import com.example.mvi_clean_architecture.presentation.ui.repos.model.UiRepoData

class ReposContract {

    sealed class ReposViewEvent : ViewEvent {
        object Refresh : ReposViewEvent()
        object Retry : ReposViewEvent()
        object BackButtonClicked : ReposViewEvent()
    }

    sealed interface ReposUiState : ViewState {
        data class Success(val repos: List<UiRepoData>) : ReposUiState
        object Error : ReposUiState
        object Loading : ReposUiState
    }

    sealed class ReposEffect : ViewSideEffect {
        sealed class Navigation : ReposEffect() {
            object Back : Navigation()
        }
    }
}
