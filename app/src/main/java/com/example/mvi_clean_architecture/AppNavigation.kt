package com.example.mvi_clean_architecture

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvi_clean_architecture.domain.model.DomainPrData
import com.example.mvi_clean_architecture.presentation.ui.prs.compose.PullRequestsScreen
import com.example.mvi_clean_architecture.presentation.ui.repos.compose.UserReposScreen
import com.example.mvi_clean_architecture.presentation.ui.users.UsersContract
import com.example.mvi_clean_architecture.presentation.ui.users.compose.UsersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController
    )
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.USERS.route) {
        composable(Screen.USERS.route) {
            UsersScreen(
                onNavigationRequested = { navigationEffect ->
                    if (navigationEffect is UsersContract.UsersEffect.Navigation.ToRepos) {
                        navController.navigateToRepos(navigationEffect.userId)
                    }
                }
            )
        }

        composable(
            Screen.REPOS.route,
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            UserReposScreen(
                onNavUp = navController::navigateUp,
                onCardClick = {
                    navController.navigate(
                        Screen.PULLREQUESTS.createRoute(
                            userId,
                            it,
                            DomainPrData.State.ALL
                        )
                    ) },
                modifier = Modifier
            )
        }

        composable(
            Screen.PULLREQUESTS.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("repoName") { type = NavType.StringType },
                navArgument("prState") { type = NavType.StringType }
            )
        ) {
            PullRequestsScreen(
                onNavUp = navController::navigateUp,
                modifier = Modifier
            )
        }
    }
}

fun NavController.navigateToRepos(userId: String) {
    navigate(route = Screen.REPOS.createRoute(userId))
}

sealed class Screen(val route: String) {
    object USERS : Screen("users")
    object REPOS : Screen("userrepos/{userId}") {
        fun createRoute(userId: String) = "userrepos/$userId"
    }
    object PULLREQUESTS : Screen("pullrequests/{userId}/{repoName}/{prState}") {
        fun createRoute(
            userId: String,
            repoName: String,
            prState: DomainPrData.State
            ) = "pullrequests/$userId/$repoName/${prState.name}"
    }
}