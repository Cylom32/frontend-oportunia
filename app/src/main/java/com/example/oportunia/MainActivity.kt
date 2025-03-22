package com.example.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.oportunia.data.datasource.UsersDataSourceImple
import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.repository.UsersRepositoryImpl
import com.example.oportunia.presentation.factory.UsersViewModelFactory
import com.example.oportunia.presentation.navigation.NavGraph

import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels {

        val mapper = UsersMapper()


        val dataSource = UsersDataSourceImple(mapper)


        val repository = UsersRepositoryImpl(dataSource, mapper)


        UsersViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                MainScreen(usersViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(usersViewModel: UsersViewModel) {
    val navController = rememberNavController()
    val hola = 0
    LaunchedEffect(Unit) {
        usersViewModel.findAllUsers()
    }

    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        text = "OportunIA",
//                        style = MaterialTheme.typography.headlineMedium
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            usersViewModel = usersViewModel
        )
    }
}


//class MainActivity : ComponentActivity() {
//    val navController = rememberNavController()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//             {
//
//                 LoginScreen(navController = rememberNavController())
//            }
//        }
//    }
//}


