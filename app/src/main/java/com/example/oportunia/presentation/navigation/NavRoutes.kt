package com.example.oportunia.presentation.navigation



sealed class NavRoutes {

    data object Login : NavRoutes() {
        const val ROUTE = "login"
    }

    data object CV : NavRoutes() {
        const val ROUTE = "cv"
    }
}
