package com.example.foodcheckai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodcheckai.presentation.home.HomeScreen
import com.example.foodcheckai.presentation.input.InputScreen
import com.example.foodcheckai.presentation.result.ResultScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToInput = {
                    navController.navigate(Screen.Input.route)
                },
                onNavigateToHowItWorks = {
                    navController.navigate(Screen.HowItWorks.route)
                }
            )
        }

        composable(route = Screen.Input.route) {
            InputScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = { ingredientText ->
                    // URL encode the ingredient text for safe navigation
                    val encoded = java.net.URLEncoder.encode(ingredientText, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screen.Result.createRoute(encoded))
                }
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("scanId") { type = NavType.StringType }
            )
        ) {
            val encodedText = it.arguments?.getString("scanId") ?: ""
            val ingredientText = URLDecoder.decode(encodedText, StandardCharsets.UTF_8.toString())

            ResultScreen(
                ingredientText = ingredientText,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.HowItWorks.route) {
            // TODO: Implement How It Works Screen in future
            // HowItWorksScreen(navController = navController)
        }
    }
}
