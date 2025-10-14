package com.app.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.countriesapp.presentation.screens.countryDetail.CharacterDetailScreen
import com.app.countriesapp.presentation.screens.characterHome.CharacterHomeScreen

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Detail : Screen("character/{characterId}") {
        fun createRoute(characterId: String) = "character/$characterId"
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun CharacterNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Home.route) {
            CharacterHomeScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.Detail.createRoute(characterId))
                },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId") ?: "1"
            CharacterDetailScreen(
                characterId = characterId,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
