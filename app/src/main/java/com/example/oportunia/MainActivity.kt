package com.example.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oportunia.presentation.navigation.NavGraph
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.screens.BottomNavigationBar
import com.example.oportunia.presentation.ui.theme.OportunIATheme
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                MainScreen(usersViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(usersViewModel: UsersViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.Log.ROUTE

    Scaffold(
        bottomBar = {
            if (currentRoute != NavRoutes.Log.ROUTE &&
                currentRoute != NavRoutes.Login.ROUTE &&
                currentRoute != NavRoutes.RegisterOption.ROUTE &&
                currentRoute != NavRoutes.RegisterInformationF.ROUTE &&
                currentRoute != NavRoutes.RegisterInformationPAndE.ROUTE &&
                currentRoute != NavRoutes.StudentInformationSettings2.ROUTE
            ) {
                BottomNavigationBar(
                    selectedScreen = currentRoute,
                    onScreenSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            usersViewModel = usersViewModel
           // studentViewModel = studentViewModel
        )
    }
}
