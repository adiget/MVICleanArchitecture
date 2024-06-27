package com.example.mvi_clean_architecture.presentation.ui.prs

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.mvi_clean_architecture.presentation.ui.MainDispatcherRule
import com.example.mvi_clean_architecture.presentation.ui.TestUseCase
import com.example.mvi_clean_architecture.domain.GetPullRequestsUseCase
import com.example.mvi_clean_architecture.domain.model.DomainPrData
import com.example.mvi_clean_architecture.domain.model.User
import com.example.mvi_clean_architecture.presentation.mapper.UserViewMapper
import com.example.mvi_clean_architecture.presentation.ui.prs.mapper.PrViewMapper
import com.example.mvi_clean_architecture.presentation.ui.prs.model.UiPrData
import com.example.mvi_clean_architecture.presentation.views.UserView
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PrsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val useCase = mockk<GetPullRequestsUseCase>()
    private val viewMapper = PrViewMapper(UserViewMapper())
    private val savedStateHandle = SavedStateHandle(
        mapOf(
            PrsViewModel.USER_ID_SAVED_STATE_KEY to USER_ID,
            PrsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
            PrsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
        )
    )
    private lateinit var viewModel: PrsViewModel

    @Test
    fun userId_repoName_prState_matchesFromSavedStateHandle() {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(testPrs)

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        assertEquals(USER_ID, viewModel.userId)
        assertEquals(REPO_NAME, viewModel.repoName)
        assertEquals(REPO_STATE, viewModel.prState)
    }

    @Test
    fun when_initialized_usecase_emits_loading_and_data() = runTest {
        val testUseCase = TestUseCase<List<DomainPrData>>()

        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns testUseCase.invoke()

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            val firstItem = awaitItem()
            assertEquals(PrsContract.PrsUiState.Loading, firstItem)

            testUseCase.sendUsers(testPrs)

            val secondItem = awaitItem()
            assertEquals(PrsContract.PrsUiState.Success(testPrsForView), secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun when_usecase_emit_empty_list_viewmodel_returns_ui_state_with_empty_list() = runTest {
        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(emptyList())

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            assertEquals(PrsContract.PrsUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun when_usecase_emit_error_viewmodel_returns_error_ui_state() = runTest {
        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flow {
            throw Exception("Error network call")
        }

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            assertEquals(PrsContract.PrsUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private const val USER_ID = "user1"
private const val REPO_NAME = "repo1"
private val REPO_STATE = "ALL"

private val testPrs = listOf(
    DomainPrData(
        1,
        "pr1 desc",
        "pr1 title",
        User("user1"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    DomainPrData(
        2,
        "pr2 desc",
        "pr2 title",
        User("user2"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    DomainPrData(
        3,
        "pr3 desc",
        "pr3 title",
        User("user3"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    )
)

private val testPrsForView = listOf(
    UiPrData(
        1,
        "pr1 title",
        "pr1 desc",
        UserView("user1"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    UiPrData(
        2,
        "pr2 title",
        "pr2 desc",
        UserView("user2"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    UiPrData(
        3,
        "pr3 title",
        "pr3 desc",
        UserView("user3"),
        "25 Oct'22",
        "25 Oct'22"
    )
)