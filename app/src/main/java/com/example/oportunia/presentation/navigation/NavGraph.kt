package com.example.oportunia.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oportunia.ui.screens.CVScreen
import com.example.oportunia.ui.screens.LoginScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.ROUTE
    ) {
        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(navController = navController)
        }

        composable(NavRoutes.CV.ROUTE) {
            CVScreen()
        }
    }
}
