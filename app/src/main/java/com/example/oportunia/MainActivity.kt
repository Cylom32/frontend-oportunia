package com.example.oportunia

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oportunia.presentation.navigation.NavGraph
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.Investigation.LanguagePreferences
import com.example.oportunia.presentation.ui.screens.BottomNavigationBar
import com.example.oportunia.presentation.ui.screens.NavegationBarCompany
import com.example.oportunia.presentation.ui.theme.OportunIATheme
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.LanguageViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private val companyViewModel: CompanyViewModel by viewModels()
    private val languageViewModel: LanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val languagePrefs = LanguagePreferences(this)
        val savedLanguage = languagePrefs.getLanguage()
        this.updateLanguage(savedLanguage.code) // Usar la función de extensión

        setContent {
            OportunIATheme {
                MainScreen(usersViewModel, studentViewModel, companyViewModel, languageViewModel)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            val languagePrefs = LanguagePreferences(newBase)
            val selectedLanguage = languagePrefs.getLanguage()

            // Aplicar idioma directamente sin función de extensión
            val locale = Locale(selectedLanguage.code)
            Locale.setDefault(locale)
            val configuration = Configuration(newBase.resources.configuration)
            configuration.setLocale(locale)
            val context = newBase.createConfigurationContext(configuration)

            super.attachBaseContext(context)
        } else {
            super.attachBaseContext(newBase)
        }
    }

    fun Activity.updateLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }




}




@Composable
fun MainScreen(
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel,
    languageViewModel: LanguageViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.Log.ROUTE

    val context = LocalContext.current
    val shouldRecreate by languageViewModel.shouldRecreate.collectAsState()

    // Efecto para recrear actividad si cambia idioma
    LaunchedEffect(shouldRecreate) {
        if (shouldRecreate && context is Activity) {
            languageViewModel.onActivityRecreated()
            context.recreate()
        }
    }

    // → CONTROL DE BOTÓN ATRÁS PARA SALIR CON DOBLE TOQUE
    var backPressedOnce by remember { mutableStateOf(false) }


    val coroutineScope = rememberCoroutineScope()

    if (currentRoute == NavRoutes.HomeScreenS.ROUTE || currentRoute == NavRoutes.CompanyInfoScreenForCompany.ROUTE) {
        BackHandler {
            if (backPressedOnce) {
                (context as? Activity)?.finish()
            } else {
                backPressedOnce = true
                Toast.makeText(context, "Presione nuevamente para salir", Toast.LENGTH_SHORT).show()

                coroutineScope.launch {
                    kotlinx.coroutines.delay(2000)
                    backPressedOnce = false
                }
            }
        }
    }


    Scaffold(
        bottomBar = {
            val ocultarBarra =
                currentRoute == NavRoutes.Log.ROUTE ||
                        currentRoute == NavRoutes.Login.ROUTE ||
                        currentRoute == NavRoutes.RegisterOption.ROUTE ||
                        currentRoute == NavRoutes.RegisterInformationF.ROUTE ||
                        currentRoute == NavRoutes.RegisterInformationPAndE.ROUTE ||
                        currentRoute == NavRoutes.StudentInformationSettings2.ROUTE ||
                        currentRoute == NavRoutes.RegisterInformationCompanyScreen.ROUTE ||
                        currentRoute == NavRoutes.RegisterCredentialsScreen.ROUTE ||
                        currentRoute == NavRoutes.EditInformationCompanyScreen.ROUTE ||
                        currentRoute == NavRoutes.GridPublicationsCompany.ROUTE ||
                        currentRoute == NavRoutes.PublicationDetailScreen.ROUTE ||
                        currentRoute == NavRoutes.RequestScreen.ROUTE

            val mostrarBarraCompany =
                currentRoute == NavRoutes.CompanyInfoScreenForCompany.ROUTE ||
                        currentRoute == NavRoutes.CompanyMessagesScreen.ROUTE ||
                        currentRoute == NavRoutes.SettingScreenCompany.ROUTE

            when {
                ocultarBarra -> {}
                mostrarBarraCompany -> {
                    NavegationBarCompany(
                        selectedScreen = currentRoute,
                        onScreenSelected = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                else -> {
                    BottomNavigationBar(
                        selectedScreen = currentRoute,
                        onScreenSelected = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            usersViewModel = usersViewModel,
            studentViewModel = studentViewModel,
            companyViewModel = companyViewModel,
            languageViewModel = languageViewModel
        )
    }
}
