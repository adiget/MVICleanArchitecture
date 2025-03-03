package com.example.mvi_clean_architecture.presentation.ui.users

import androidx.lifecycle.viewModelScope
import com.example.mvi_clean_architecture.common.Result
import com.example.mvi_clean_architecture.common.asResult
import com.example.mvi_clean_architecture.domain.GetGitHubUsersUseCase
import com.example.mvi_clean_architecture.presentation.ui.base.BaseViewModel
import com.example.mvi_clean_architecture.presentation.ui.users.mapper.UserViewMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject internal constructor(
    val viewMapper: UserViewMapper,
    val useCase: GetGitHubUsersUseCase
) : BaseViewModel<UsersContract.UsersViewEvent, UsersContract.UsersUiState, UsersContract.UsersEffect>() {

    init {
        getUsers()
    }

    override fun setInitialState() = UsersContract.UsersUiState.Loading

    override fun handleEvents(event: UsersContract.UsersViewEvent) {
        when (event) {
            is UsersContract.UsersViewEvent.UserSelection -> setEffect {
                UsersContract.UsersEffect.Navigation.ToRepos(event.user.userId)
            }
            is UsersContract.UsersViewEvent.Refresh,
            is UsersContract.UsersViewEvent.Retry -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            useCase()
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> setState { UsersContract.UsersUiState.Loading }
                        is Result.Success -> setState {
                            UsersContract.UsersUiState.Success(
                                result.data.map { viewMapper.mapToView(it) }
                            )
                        }
                        is Result.Error -> setState { UsersContract.UsersUiState.Error }
                    }
                }
        }
    }
}
