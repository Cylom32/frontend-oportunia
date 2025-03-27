package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.theme.lilBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel



@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.oportunia.presentation.ui.theme.lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(com.example.oportunia.presentation.ui.theme.lilGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Encabezado
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                        clip = false
                    )
                    .background(
                        color = com.example.oportunia.presentation.ui.theme.lilBlue,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.titleSettings),
                    color = com.example.oportunia.presentation.ui.theme.walterWhite,
                    fontSize = 39.sp,
                    //  fontWeight = FontWeight.Bold
                )
            }



            Spacer(modifier = Modifier.height(25.dp))
            // Buttons Section
            ButtonSectionSettings(navController = navController, usersViewModel = usersViewModel)
        }
    }


}

@Composable

fun ButtonSectionSettings(navController: NavHostController,usersViewModel:UsersViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Eliminamos .height(90.dp) para que la columna crezca según el contenido
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Handle Edit Account action */
            navController.navigate(NavRoutes.StudentInformationSettings2.ROUTE)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp), // Reducimos la altura para que sea consistente con los otros botones
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.oportunia.presentation.ui.theme.walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Edit Account",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Cuenta", fontSize = 22.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { /* Handle Edit Language action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.oportunia.presentation.ui.theme.walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Edit Account",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(34.dp))
            Text(text = "Idioma", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Logout action */
                usersViewModel.logout() // Cerrar sesión
                navController.navigate(NavRoutes.Log.ROUTE) { // Redirigir a la pantalla de login
                    popUpTo(navController.graph.startDestinationId) // Limpiar el backstack
                    launchSingleTop = true// Evitar multiples instancias
                }



                },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.oportunia.presentation.ui.theme.walterWhite, // Cambiamos el color de fondo a walterWhite
                contentColor = Color.Black // Cambiamos el color del contenido a negro
            ),
            shape = RoundedCornerShape(34.dp), // Añadimos la misma forma que el botón "Cuenta"
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Añadimos la misma elevación
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(Color.Black) // Cambiamos el tint del ícono a negro para que coincida con el contentColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Logout",
                fontSize = 22.sp,
                color = Color.Black // Aseguramos que el texto sea negro
            )
        }
    }
}
