package com.example.beverly_hills_company_test_task

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beverly_hills_company_test_task.presentation.ui.navigation.Routes
import com.example.beverly_hills_company_test_task.presentation.ui.screens.Game
import com.example.beverly_hills_company_test_task.presentation.ui.screens.Home
import com.example.beverly_hills_company_test_task.presentation.ui.screens.Splash
import com.example.beverly_hills_company_test_task.presentation.ui.screens.WebView
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val oneSignalKey = "~ ~ ~ ~ ONESIGNAL KEY ~ ~ ~ ~"

        OneSignal.initWithContext(this)
        OneSignal.setAppId(oneSignalKey)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

//        OneSignal.setNotificationOpenedHandler {
//            val prefs = SharedPrefs(this)
//            val id = it.notification.notificationId
//            prefs.setNotID(notId = id)
//        }

    }
}

@Composable
fun TestAppUi(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Splash.route) {
            Splash(
                onWebViewOpen = {
                    navController.popBackStack()
                    navController.navigate(Routes.WebView.route)
                },
                onGameOpen = {
                    navController.popBackStack()
                    navController.navigate(Routes.Home.route)
                }
            )
        }
        composable(Routes.Home.route) {
            Home(
                onGameCLick = { navController.navigate(Routes.Game.route) },
                onWebViewClick = { navController.navigate(Routes.WebView.route) },
                onSplashClick = { navController.navigate(Routes.Splash.route) },
                )
        }
        composable(Routes.Game.route) {
            Game(
                onHomeScreenClick = { navController.popBackStack() }
            )
        }
        composable(Routes.WebView.route) {
            WebView(
                onGameOpen = {
                    navController.popBackStack()
                    navController.navigate(Routes.Home.route)
                }
            )
        }
    }
}