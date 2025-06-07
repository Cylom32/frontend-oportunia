package com.example.oportunia.presentation.ui.screens
import androidx.compose.foundation.background
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
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.oportunia.presentation.ui.theme.*




@Composable
fun LanguageOptionsScreenSC(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = NavRoutes.Settings.ROUTE,
                onScreenSelected = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(lilGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start  = 0.dp,
                        top    = 0.dp,
                        end    = 0.dp,
                        bottom = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                            clip = false
                        )
                        .gradientBackgroundBlue(
                            gradientColorsBlue,
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.LanguageOptiongTitle),
                        color = walterWhite,
                        fontSize = 39.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                ButtonSectionSettings()

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { /* acción aplicar */ },
                    modifier = Modifier
                        .width(200.dp)
                        .height(76.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = walterWhite,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(34.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ApplyButtonText),
                        color = blackPanter,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}






@Composable
private fun ButtonSectionSettings() {
    val languages = listOf("Español", "English", "Français", "Deutsch")
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    val buttonWidth = 500.dp
    val buttonHeight = 106.dp
    val itemHeight = 56.dp
    val itemSpacing = 4.dp

    Box {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .width(buttonWidth)
                .height(buttonHeight),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = selectedLanguage, fontSize = 18.sp)
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(buttonWidth),
            shape = RoundedCornerShape(34.dp),
            containerColor = Color.Transparent,
            tonalElevation = 4.dp,
            shadowElevation = 0.dp,
            border = null
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.Black) {
                languages.forEach { language ->
                    Button(
                        onClick = {
                            selectedLanguage = language
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight)
                            .padding(vertical = itemSpacing),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = walterWhite,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(34.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(text = stringResource(R.string.language_text), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}


