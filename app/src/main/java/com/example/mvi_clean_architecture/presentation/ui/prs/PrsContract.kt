package com.example.mvi_clean_architecture.presentation.ui.prs

import com.example.mvi_clean_architecture.presentation.ui.base.ViewEvent
import com.example.mvi_clean_architecture.presentation.ui.base.ViewSideEffect
import com.example.mvi_clean_architecture.presentation.ui.base.ViewState
import com.example.mvi_clean_architecture.presentation.ui.prs.model.UiPrData

class PrsContract {

    sealed class PrsViewEvent : ViewEvent {
        object Refresh : PrsViewEvent()
        object Retry : PrsViewEvent()
        object BackButtonClicked : PrsViewEvent()
    }

    sealed interface PrsUiState : ViewState {
        data class Success(val prs: List<UiPrData>) : PrsUiState
        object Error : PrsUiState
        object Loading : PrsUiState
    }

    sealed class PrsEffect : ViewSideEffect {
        sealed class Navigation : PrsEffect() {
            object Back : Navigation()
        }
    }
}
