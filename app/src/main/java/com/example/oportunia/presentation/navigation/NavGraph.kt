package com.example.oportunia.presentation.navigation

import LogScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oportunia.ui.screens.CVScreen
import com.example.oportunia.ui.screens.HomeScreen
import com.example.oportunia.ui.screens.LoginScreen
import com.example.oportunia.ui.screens.NotificationsScreen
import com.example.oportunia.ui.screens.RegisterOptionScreen
import com.example.oportunia.ui.screens.SettingScreen
import com.example.oportunia.ui.viewmodel.StudentViewModel
import com.example.oportunia.ui.viewmodel.UsersViewModel



@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel
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

        composable(NavRoutes.Log.ROUTE) {
            LogScreen(
                navController = navController,
                usersViewModel = usersViewModel, studentViewModel = studentViewModel,
            )
        }


        composable(NavRoutes.CV.ROUTE) {
            CVScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }

        composable(NavRoutes.Home.ROUTE) {
            HomeScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(NavRoutes.Notifications.ROUTE) {
            NotificationsScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(NavRoutes.Settings.ROUTE) {
            SettingScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }


        composable(NavRoutes.RegisterOption.ROUTE) {
            RegisterOptionScreen(navController = navController)
        }


    }
}
