package com.example.oportunia.presentation.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oportunia.presentation.ui.screens.CVScreen
import com.example.oportunia.presentation.ui.screens.CompanyInfoScreenForCompany
import com.example.oportunia.presentation.ui.screens.CompanyInfoScreenS
import com.example.oportunia.presentation.ui.screens.CompanyMessagesScreen
import com.example.oportunia.presentation.ui.screens.CvAnalisys
import com.example.oportunia.presentation.ui.screens.EditInformationCompanyScreen
import com.example.oportunia.presentation.ui.screens.EditUCVScreen
import com.example.oportunia.presentation.ui.screens.GridPublicationsCompany
import com.example.oportunia.presentation.ui.screens.GridPublicationsScreenS
import com.example.oportunia.presentation.ui.screens.HomeScreen
import com.example.oportunia.presentation.ui.screens.HomeScreenS
import com.example.oportunia.presentation.ui.screens.IntershipScreen
import com.example.oportunia.presentation.ui.screens.LanguageOptionsScreenSC
import com.example.oportunia.presentation.ui.screens.LogScreen
import com.example.oportunia.presentation.ui.screens.NotificationsScreen
import com.example.oportunia.presentation.ui.screens.PublicationDetailScreen
import com.example.oportunia.presentation.ui.screens.RegisterCredentialsScreen
import com.example.oportunia.presentation.ui.screens.RegisterInformationCompanyScreen
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreen
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreenF
import com.example.oportunia.presentation.ui.screens.RegisterOptionScreenPAndE
import com.example.oportunia.presentation.ui.screens.RequestScreen
import com.example.oportunia.presentation.ui.screens.SentRequestScreen
import com.example.oportunia.presentation.ui.screens.SettingScreen
import com.example.oportunia.presentation.ui.screens.SettingScreenCompany
import com.example.oportunia.presentation.ui.screens.StudentInformationSettings
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.LanguageViewModel
//import com.example.oportunia.presentation.ui.screens.StudentInformationSettings2
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel,
    languageViewModel: LanguageViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Log.ROUTE
    ) {

        composable(NavRoutes.GridPublicationsScreenS.ROUTE) {
            GridPublicationsScreenS(
                navController = navController,
                studentViewModel = studentViewModel,
                usersViewModel = usersViewModel,
                companyViewModel = companyViewModel
            )
        }
        // pa un commit

        composable(NavRoutes.Log.ROUTE) {
            LogScreen(
                navController,
                usersViewModel,
                studentViewModel,
                companyViewModel
            )
        }

        composable(NavRoutes.SettingScreenCompany.ROUTE) {
            SettingScreenCompany(
                navController = navController,
                usersViewModel = usersViewModel,
                languageViewModel=languageViewModel
            )
        }

        composable(NavRoutes.GridPublicationsCompany.ROUTE) {
            GridPublicationsCompany(
                navController = navController,
                studentViewModel = studentViewModel,
                usersViewModel = usersViewModel,
                companyViewModel = companyViewModel
            )
        }

        composable(NavRoutes.CompanyInfoScreenForCompany.ROUTE) {
            CompanyInfoScreenForCompany(
                navController,
                usersViewModel,
                studentViewModel,
                companyViewModel

            )
        }


        composable(NavRoutes.PublicationDetailScreen.ROUTE) {
            PublicationDetailScreen(
                navController = navController,
                studentViewModel = studentViewModel,
                usersViewModel = usersViewModel,
                companyViewModel = companyViewModel,
                paddingValues = paddingValues
            )
        }


        composable(NavRoutes.IntershipScreen.ROUTE) {

            IntershipScreen(
                navController = navController,
                studentViewModel = studentViewModel,
                usersViewModel = usersViewModel,
                companyViewModel = companyViewModel
            )

        }

        composable(NavRoutes.CompanyMessagesScreen.ROUTE) {
            CompanyMessagesScreen(
                navController = navController,
                companyViewModel = companyViewModel
            )
        }

        composable(NavRoutes.EditInformationCompanyScreen.ROUTE) {
            EditInformationCompanyScreen(
                navController = navController,
                usersViewModel = usersViewModel,
                companyViewModel = companyViewModel
            )
        }


        composable(NavRoutes.LanguageOptionsSC.ROUTE) {
            LanguageOptionsScreenSC(
                navController = navController,
                // usersViewModel = usersViewModel
            )
        }

        composable(NavRoutes.CvAnalisys.ROUTE) {
            CvAnalisys(
                studentViewModel = studentViewModel,
                paddingValues = paddingValues
            )
        }


        composable(NavRoutes.CV.ROUTE) {
            CVScreen(
                studentViewModel, navController
            )
        }

        composable(NavRoutes.Home.ROUTE) {
            HomeScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }

        composable(NavRoutes.RequestScreen.ROUTE) {
            RequestScreen(
                navController = navController,
                userViewModel = usersViewModel,
                studentViewModel = studentViewModel,
                companyViewModel = companyViewModel
            )
        }


        composable(NavRoutes.HomeScreenS.ROUTE) {
            HomeScreenS(
                companyViewModel,
                usersViewModel,
                navController
            )
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
                languageViewModel = languageViewModel
            )
        }


        composable(NavRoutes.RegisterOption.ROUTE) {
            RegisterOptionScreen(usersViewModel, navController)
        }

        composable(NavRoutes.SentRequestScreen.ROUTE) {
            SentRequestScreen(
                navController = navController,
                userViewModel = usersViewModel,
                studentViewModel = studentViewModel,
                companyViewModel = companyViewModel
            )
        }

        composable(NavRoutes.CompanyInfoScreenS.ROUTE) {
            CompanyInfoScreenS(
                navController,
                usersViewModel,
                studentViewModel,
                companyViewModel
            )
        }

//        composable (NavRoutes.LanguageOptionsSC.ROUTE){
//            LanguageOptionsSC()
//        }

        composable(NavRoutes.RegisterInformationF.ROUTE) {
            RegisterOptionScreenF(navController, usersViewModel)
        }

        composable(NavRoutes.RegisterInformationPAndE.ROUTE) {
            RegisterOptionScreenPAndE(usersViewModel, studentViewModel, navController)
        }

        composable(NavRoutes.StudentInformationSettings2.ROUTE) {
            StudentInformationSettings(
                navController,
                usersViewModel,
                studentViewModel,
                companyViewModel
            )
        }

        composable(NavRoutes.EditUCVScreen.ROUTE) {
            EditUCVScreen(studentViewModel, usersViewModel)
        }


        composable(NavRoutes.RegisterInformationCompanyScreen.ROUTE) {
            RegisterInformationCompanyScreen(
                navController,
                usersViewModel
            )
        }

        composable(NavRoutes.RegisterCredentialsScreen.ROUTE) {
            RegisterCredentialsScreen(
                navController,
                usersViewModel
            )
        }
    }
}
