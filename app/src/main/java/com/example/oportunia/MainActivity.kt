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
import com.example.oportunia.data.datasource.UsersDataSourceImple
import com.example.oportunia.data.datasource.StudentDataSourceImple
import com.example.oportunia.data.datasource.UniversityDataSourceImpl
import com.example.oportunia.data.datasource.model.UniversityRepositoryImpl
import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.mapper.StudentMapper
import com.example.oportunia.data.mapper.UniversityMapper
import com.example.oportunia.data.repository.UsersRepositoryImpl
import com.example.oportunia.data.repository.StudentRepositoryImpl
import com.example.oportunia.presentation.factory.UsersViewModelFactory
import com.example.oportunia.presentation.factory.StudentViewModelFactory
import com.example.oportunia.presentation.navigation.NavGraph
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.screens.BottomNavigationBar

import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.viewmodel.StudentViewModel
import com.example.oportunia.ui.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels {

        val mapper = UsersMapper()


        val dataSource = UsersDataSourceImple(mapper)


        val repository = UsersRepositoryImpl(dataSource, mapper)


        UsersViewModelFactory(repository)
    }


    private val studentViewModel: StudentViewModel by viewModels {
        val studentMapper = StudentMapper()

        val universityMapper = UniversityMapper()



        val studentDataSource = StudentDataSourceImple(studentMapper)

        val studentRepository = StudentRepositoryImpl(studentDataSource, studentMapper)

        /////////////////////

        val universityDataSource = UniversityDataSourceImpl(universityMapper)

        val universityRepository = UniversityRepositoryImpl(universityDataSource,universityMapper)

/////////////////////////////////
        StudentViewModelFactory(studentRepository,universityRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                MainScreen(usersViewModel,studentViewModel)
            }
        }
    }
}


@Composable
fun MainScreen(usersViewModel: UsersViewModel,studentViewModel: StudentViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.Log.ROUTE
    LaunchedEffect(Unit) {
       // usersViewModel.findAllUsers()
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != NavRoutes.Log.ROUTE &&
                currentRoute != NavRoutes.Login.ROUTE &&
                currentRoute != NavRoutes.RegisterOption.ROUTE &&
                currentRoute != NavRoutes.RegisterInformationF.ROUTE &&
                currentRoute != NavRoutes.RegisterInformationPAndE.ROUTE) {
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
            usersViewModel = usersViewModel,
            studentViewModel = studentViewModel,
        )
    }
}



