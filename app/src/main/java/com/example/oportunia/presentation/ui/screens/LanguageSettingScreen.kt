package com.example.oportunia.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.PasswordLabel
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.launch


@Preview
@Composable
fun LanguageSettingScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val titleFontSize = when {
        screenWidth < 360.dp -> 36.sp
        screenWidth < 600.dp -> 48.sp
        else -> 64.sp
    }

    val expanded = remember { mutableStateOf(false) }
    val selectedLanguage = remember { mutableStateOf("Seleccionar idioma") }

    val languageList = listOf(
        "Español", "Inglés", "Francés", "Alemán",
        "Portugués", "Italiano", "Chino"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lilGray)
                    .padding(bottom = 100.dp),
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
                        .gradientBackgroundBlue(
                            gradientColorsBlue,
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.LanguageSettingTitle),
                        fontSize = titleFontSize,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))

                // Botón con lista desplegable de idiomas
                Box {
                    Button(
                        onClick = { expanded.value = true },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = com.example.oportunia.presentation.ui.theme.walterWhite,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.world),
                            contentDescription = "Idioma",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )

                        Spacer(modifier = Modifier.width(30.dp))

                        Text(
                            text = selectedLanguage.value,
                            fontSize = 22.sp,
                            color = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.White)
                    ) {
                        languageList.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language) },
                                onClick = {
                                    selectedLanguage.value = language
                                    expanded.value = false
                                    Log.d("IdiomaSeleccionado", "🌐 Idioma seleccionado: $language")
                                }
                            )
                        }
                    }
                }
            }

            // Botón "Guardar Cambios"
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.linearGradient(gradientColorsBlue)
                    )
                    .clickable {
                        Log.d("GuardarCambios", "✅ Guardando idioma: ${selectedLanguage.value}")
                    }
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Guardar Cambios",
                    fontSize = 20.sp,
                    color = com.example.oportunia.presentation.ui.theme.walterWhite,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
