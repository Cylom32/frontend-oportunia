package com.example.oportunia.presentation.navigation



sealed class NavRoutes {

    data object Login : NavRoutes() {
        const val ROUTE = "login"
    }

    data object CV : NavRoutes() {
        const val ROUTE = "cv"
    }

    data object Log : NavRoutes() {
        const val ROUTE = "log"

    }

    data object RegisterOption : NavRoutes() {
        const val ROUTE = "registerOption"
    }
}
