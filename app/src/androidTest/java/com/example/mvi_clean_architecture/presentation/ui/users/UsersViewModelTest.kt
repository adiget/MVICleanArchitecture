package com.example.mvi_clean_architecture.presentation.ui.users

import app.cash.turbine.test
import com.example.mvi_clean_architecture.presentation.ui.MainDispatcherRule
import com.example.mvi_clean_architecture.presentation.ui.TestUseCase
import com.example.mvi_clean_architecture.domain.GetGitHubUsersUseCase
import com.example.mvi_clean_architecture.domain.model.DomainUserData
import com.example.mvi_clean_architecture.presentation.ui.users.mapper.UserViewMapper
import com.example.mvi_clean_architecture.presentation.ui.users.model.UiUserData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UsersViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val viewMapper = UserViewMapper()
    private val useCase = mockk<GetGitHubUsersUseCase>()
    private lateinit var viewModel: UsersViewModel

    @Test
    fun when_initialized_usecase_emits_loading_and_data() = runTest {
        val testUseCase = TestUseCase<List<DomainUserData>>()

        every { useCase() } returns testUseCase.invoke()

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            val firstItem = awaitItem()
            assertEquals(UsersContract.UsersUiState.Loading, firstItem)

            testUseCase.sendUsers(testUsers)

            val secondItem = awaitItem()
            assertEquals(UsersContract.UsersUiState.Success(testUsersForView), secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun when_usecase_emit_empty_list_viewmodel_returns_ui_state_with_empty_list() = runTest {
        every { useCase() } returns flowOf(emptyList())

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            assertEquals(UsersContract.UsersUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun when_usecase_emit_error_viewmodel_returns_error_ui_state() = runTest {
        every { useCase() } returns flow {
            throw Exception("Error network call")
        }

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            assertEquals(UsersContract.UsersUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private val testUsers = listOf(
    DomainUserData(
        userId = "1",
        avatarUrl = "http://test1AvatarUrl",
        htmlUrl = "http://test1HtmlUrl"
    ),
    DomainUserData(
        userId = "2",
        avatarUrl = "http://test2AvatarUrl",
        htmlUrl = "http://test2HtmlUrl"
    ),
    DomainUserData(
        userId = "3",
        avatarUrl = "http://test3AvatarUrl",
        htmlUrl = "http://test3HtmlUrl"
    )
)

private val testUsersForView = listOf(
    UiUserData(
        userId = "1",
        avatarUrl = "http://test1AvatarUrl",
        htmlUrl = "http://test1HtmlUrl"
    ),
    UiUserData(
        userId = "2",
        avatarUrl = "http://test2AvatarUrl",
        htmlUrl = "http://test2HtmlUrl"
    ),
    UiUserData(
        userId = "3",
        avatarUrl = "http://test3AvatarUrl",
        htmlUrl = "http://test3HtmlUrl"
    )
)