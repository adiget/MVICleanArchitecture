package com.example.mvi_clean_architecture.data.repository

import app.cash.turbine.test
import com.example.mvi_clean_architecture.data.datasource.GitDataSourceFactory
import com.example.mvi_clean_architecture.data.datasource.GithubAppNetworkDataSource
import com.example.mvi_clean_architecture.data.datasource.network.GitRemote
import com.example.mvi_clean_architecture.data.mapper.PrDataMapper
import com.example.mvi_clean_architecture.data.mapper.RepoDataMapper
import com.example.mvi_clean_architecture.data.mapper.UserDataMapper
import com.example.mvi_clean_architecture.data.model.GithubUser
import com.example.mvi_clean_architecture.data.model.SingleRepoEntity
import com.example.mvi_clean_architecture.domain.GithubRepository
import com.example.mvi_clean_architecture.domain.model.DomainRepoData
import com.example.mvi_clean_architecture.domain.model.DomainUserData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GithubRepositoryTest{
    private val repoMapper = RepoDataMapper()
    private val prMapper = PrDataMapper()
    private val userMapper = UserDataMapper()
    private val gitRemote = mockk<GitRemote>()
    private val githubAppNetworkDataSource = GithubAppNetworkDataSource(gitRemote)
    private val gitDataSourceFactory = GitDataSourceFactory(githubAppNetworkDataSource)

    private lateinit var subject: GithubRepository

    @Before
    fun setup(){
        subject = GithubRepositoryImpl(
            repoMapper,
            prMapper,
            userMapper,
            gitDataSourceFactory,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `when getGitHubUsers called then should get users from the dats source`() = runTest {
        every {
            gitRemote.getUsers()
        } returns flowOf(testGitHubUsers)

        val users: Flow<List<DomainUserData>> = subject.getGitHubUsers()

        users.test {
            assertEquals(testDomainUserData, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when getUserGitRepositories called then should get repos from the dats source`() = runTest {
        every {
            gitRemote.getUserGitRepositories(USER_ID)
        } returns flowOf(testUserRepos)

        val repos: Flow<List<DomainRepoData>> = subject.getUserGitRepositories(USER_ID)

        repos.test {
            assertEquals(testDomainReposData, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private companion object {
        const val USER_ID = "userId"
    }
}

private val testGitHubUsers = listOf(
    GithubUser(
        avatarUrl = "testAvatarUrl",
        eventsUrl = "testEventsUrl",
        followersUrl = "testFollowersUrl",
        followingUrl = "testFollowingUrl",
        gistsUrl = "testGistsUrl",
        gravatarId = "testGravatarId",
        htmlUrl = "testHtmlUrl",
        id = 1,
        login = "testLogin",
        nodeId = "testNodeId",
        organizationsUrl = "testOrganizationsUrl",
        receivedEventsUrl = "testReceivedEventsUrl",
        reposUrl = "testReposUrl",
        siteAdmin = false,
        starredUrl = "testStarredUrl",
        subscriptionsUrl = "testSubscriptionsUrl",
        type = "testType",
        url = "testUrl"
    )
)

private val testDomainUserData = listOf(
    DomainUserData(
        userId = "testLogin",
        avatarUrl = "testAvatarUrl",
        htmlUrl = "testHtmlUrl"
    )
)

private val testUserRepos = listOf(
    SingleRepoEntity(
        repoName = "testRepoName",
        repoDescription = "testRepoDescription",
        openIssuesCount = 1,
        forksCount = 1,
        defaultBranch = "testDefaultBranch"
    )
)

private val testDomainReposData = listOf(
    DomainRepoData(
        repoName = "testRepoName",
        repoDescription = "testRepoDescription",
        openIssuesCount = 1,
        forksCount = 1,
        defaultBranch = "testDefaultBranch"
    )
)