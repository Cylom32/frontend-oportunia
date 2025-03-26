package com.example.oportunia.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.theme.OportunIATheme


@Composable
fun BottomNavigationBar(selectedScreen: String, onScreenSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Cv") },
            label = { Text("Cv") },
            selected = selectedScreen == NavRoutes.CV.ROUTE,
            onClick = { onScreenSelected(NavRoutes.CV.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedScreen == NavRoutes.Home.ROUTE,
            onClick = { onScreenSelected(NavRoutes.Home.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Notifications") },
            label = { Text("Notifications") },
            selected = selectedScreen == NavRoutes.Notifications.ROUTE,
            onClick = { onScreenSelected(NavRoutes.Notifications.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = selectedScreen == "Settings",
            onClick = { onScreenSelected("Settings") }
        )
    }
}

