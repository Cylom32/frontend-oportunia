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
import com.example.oportunia.ui.screens.RegisterOptionScreenF
import com.example.oportunia.ui.screens.RegisterOptionScreenPAndE
import com.example.oportunia.ui.screens.SettingScreen
import com.example.oportunia.ui.screens.StudentInformationSettings2
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


        composable(NavRoutes.Log.ROUTE) {
            LogScreen(
                navController = navController,
                usersViewModel = usersViewModel, studentViewModel,
            )
        }


        composable(NavRoutes.CV.ROUTE) {
            CVScreen(
                modifier = Modifier.padding(paddingValues),
                studentViewModel
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
                modifier = Modifier.padding(paddingValues),
                navController = navController, // Pasamos el NavController
                usersViewModel = usersViewModel,
            )
        }


        composable(NavRoutes.RegisterOption.ROUTE) {
            RegisterOptionScreen(navController)
        }

        composable(NavRoutes.RegisterInformationF.ROUTE) {
            RegisterOptionScreenF(studentViewModel, navController)
        }

        composable(NavRoutes.RegisterInformationPAndE.ROUTE) {
            RegisterOptionScreenPAndE(studentViewModel, usersViewModel, navController)


        }

        composable(NavRoutes.StudentInformationSettings2 .ROUTE) {
            StudentInformationSettings2(studentViewModel, navController)
        }




    }
}
