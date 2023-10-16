package com.example.beverly_hills_company_test_task.presentation.ui.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Game : Routes("game")
    object WebView : Routes("webv")

}