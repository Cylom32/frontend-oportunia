package com.example.oportunia.presentation.navigation

import LogScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oportunia.ui.screens.CVScreen
import com.example.oportunia.ui.screens.LoginScreen
import com.example.oportunia.ui.viewmodel.UsersViewModel



@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    usersViewModel: UsersViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Log.ROUTE
    ) {
        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }


        composable(NavRoutes.CV.ROUTE) {
            CVScreen()
        }

        composable(NavRoutes.Log.ROUTE) {
            LogScreen(
                navController = navController,
                usersViewModel = usersViewModel // ← ESTE es el punto
            )
        }


    }
}
