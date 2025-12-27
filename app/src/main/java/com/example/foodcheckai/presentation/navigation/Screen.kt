package com.example.foodcheckai.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Input : Screen("input")
    data object Result : Screen("result/{scanId}") {
        fun createRoute(scanId: String) = "result/$scanId"
    }
    data object HowItWorks : Screen("how_it_works")
}
