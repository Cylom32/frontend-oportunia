package com.example.oportunia.presentation.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oportunia.presentation.ui.screens.CVScreen
import com.example.oportunia.presentation.ui.screens.EditUCVScreen
import com.example.oportunia.presentation.ui.screens.HomeScreen
import com.example.oportunia.presentation.ui.screens.HomeScreenS
import com.example.oportunia.presentation.ui.screens.LogScreen
import com.example.oportunia.presentation.ui.screens.NotificationsScreen
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreen
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreenF
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreenPAndE
import com.example.oportunia.presentation.ui.screens.SentRequestScreen
import com.example.oportunia.presentation.ui.screens.SettingScreen
import com.example.oportunia.presentation.ui.screens.StudentInformationSettings2
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    usersViewModel: UsersViewModel
    //studentViewModel: StudentViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HomeScreenS.ROUTE
    ) {


        composable(NavRoutes.Log.ROUTE) {
            LogScreen(
                navController = navController,
                usersViewModel = usersViewModel
                //studentViewModel,
            )
        }


//        composable(NavRoutes.CV.ROUTE) {
//            CVScreen(
//                studentViewModel, navController
//            )
//        }

        composable(NavRoutes.Home.ROUTE) {
            HomeScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }

        composable(NavRoutes.HomeScreenS.ROUTE) {
            HomeScreenS()
        }

        composable(NavRoutes.Notifications.ROUTE) {
            NotificationsScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(NavRoutes.Settings.ROUTE) {
            SettingScreen(
                navController = navController,
                usersViewModel = usersViewModel,
            )
        }


        composable(NavRoutes.RegisterOption.ROUTE) {
            RegisterOptionScreen(navController)
        }

        composable(NavRoutes.SentRequestScreen.ROUTE) {
            SentRequestScreen()
        }

//        composable(NavRoutes.RegisterInformationF.ROUTE) {
//            RegisterOptionScreenF(studentViewModel, navController)
//        }
//
//        composable(NavRoutes.RegisterInformationPAndE.ROUTE) {
//            RegisterOptionScreenPAndE(studentViewModel, usersViewModel, navController)
//
//
//        }
//
//        composable(NavRoutes.StudentInformationSettings2 .ROUTE) {
//            StudentInformationSettings2(studentViewModel, navController)
//        }
//
//        composable(NavRoutes.EditUCVScreen.ROUTE) {
//            EditUCVScreen(studentViewModel)
//        }


    }
}
