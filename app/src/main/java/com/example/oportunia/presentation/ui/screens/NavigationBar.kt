package com.example.oportunia.presentation.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import com.example.oportunia.presentation.navigation.NavRoutes


@Composable
fun BottomNavigationBar(selectedScreen: String, onScreenSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Cv") },

            selected = selectedScreen == NavRoutes.CV.ROUTE,
            onClick = { onScreenSelected(NavRoutes.CV.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },

            selected = selectedScreen == NavRoutes.HomeScreenS.ROUTE,
            onClick = { onScreenSelected(NavRoutes.HomeScreenS.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Notifications") },
        //    label = { Text("Notifications") },
            selected = selectedScreen == NavRoutes.Notifications.ROUTE,
            onClick = { onScreenSelected(NavRoutes.SentRequestScreen.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
         //   label = { Text("Settings") },
            selected = selectedScreen == "Settings",
            onClick = { onScreenSelected("Settings") }
        )
    }
}

