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
fun NavegationBarCompany(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = selectedScreen == NavRoutes.HomeScreenS.ROUTE,
            onClick = { onScreenSelected(NavRoutes.HomeScreenS.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Mensajes") },
            selected = selectedScreen == NavRoutes.Notifications.ROUTE,
            onClick = { onScreenSelected(NavRoutes.Notifications.ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = selectedScreen == NavRoutes.SettingScreenCompany.ROUTE,
            onClick = { onScreenSelected(NavRoutes.SettingScreenCompany.ROUTE) }
        )
    }
}
