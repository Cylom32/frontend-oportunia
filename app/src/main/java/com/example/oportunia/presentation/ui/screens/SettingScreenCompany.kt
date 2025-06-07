package com.example.oportunia.presentation.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.cloudinary.CloudinaryService
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.viewmodel.LanguageViewModel
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun SettingScreenCompany(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    languageViewModel: LanguageViewModel
) {







    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lilGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                        clip = false
                    )
                    .gradientBackgroundBlue(gradientColorsBlue, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
//,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.titleSettings),
                    color = walterWhite,
                    fontSize = 39.sp
                )
            }



            Spacer(modifier = Modifier.height(25.dp))

            ButtonSectionSettingss(navController,usersViewModel,languageViewModel= languageViewModel)
        }
    }


}

@Composable

fun ButtonSectionSettingss(navController: NavHostController,usersViewModel:UsersViewModel,languageViewModel: LanguageViewModel) {

    val currentLanguage by languageViewModel.currentLanguage.collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false)}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Handle Edit Account action */
                navController.navigate(NavRoutes.EditInformationCompanyScreen.ROUTE)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
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
            Text(text = stringResource(R.string.account_text), fontSize = 22.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))






        Button(
            onClick = { showLanguageDialog= true },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Language",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(34.dp))
            Text(text = stringResource(R.string.language_text), fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))







        Button(
            onClick = {
                usersViewModel.logout()
                navController.navigate(NavRoutes.Log.ROUTE) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.logout_text),
                fontSize = 22.sp,
                color = Color.Black
            )
        }


        // Diálogo para seleccionar idioma
        if (showLanguageDialog) {
            LanguageSelectionDialog(
                currentLanguage = currentLanguage,
                onLanguageSelected = { language ->
                    languageViewModel.changeLanguage(language)
                    showLanguageDialog = false
                },
                onDismiss = { showLanguageDialog = false }
            )
        }




    }
}
