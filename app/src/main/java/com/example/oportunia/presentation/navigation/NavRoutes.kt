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

    data object Home : NavRoutes() {
        const val ROUTE = "home"

    }

    data object Settings : NavRoutes() {
        const val ROUTE = "settings"
    }


    data object Notifications : NavRoutes() {
        const val ROUTE = "notifications"
    }

    data object RegisterOption : NavRoutes() {
        const val ROUTE = "registerOption"
    }

    data object RegisterInformationF : NavRoutes() {
        const val ROUTE = "registerInformationF"
    }

    data object RegisterInformationPAndE : NavRoutes() {
        const val ROUTE = "registerInformationPAndE"
    }
    data object StudentInformationSettings2 : NavRoutes() {
        const val ROUTE = "studentInformationSettings2"
    }

    data object EditUCVScreen : NavRoutes() {

        const val ROUTE = "editUCVScreen"

    }

    data object SentRequestScreen : NavRoutes() {
        const val ROUTE = "sentRequestScreen"
    }

    data object HomeScreenS : NavRoutes() {
        const val ROUTE = "homeScreenS"

    }

    data object LanguageOptionsSC : NavRoutes() {
        const val ROUTE = "languageOptionsSC"
    }

    data object CompanyInfoScreenS : NavRoutes() {
        const val ROUTE = "companyInfoScreenS"
    }

    data object GridPublicationsScreenS : NavRoutes() {
        const val ROUTE = "gridPublicationsScreenS"
    }

    data object IntershipScreen : NavRoutes() {
        const val ROUTE = "intershipScreen"
    }

    data object RequestScreen : NavRoutes() {
        const val ROUTE = "requestScreen"
    }

    data object RegisterInformationCompanyScreen : NavRoutes() {
        const val ROUTE = "registerInformationCompanyScreen"
    }

    data object RegisterCredentialsScreen : NavRoutes() {
        const val ROUTE = "registerCredentialsScreen"
    }


}
