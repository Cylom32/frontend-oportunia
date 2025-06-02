// RegisterInformationCompanyScreen.kt
package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel

@Composable
fun RegisterInformationCompanyScreen(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {
    var companyName by remember { mutableStateOf(TextFieldValue("")) }
    var social1 by remember { mutableStateOf(TextFieldValue("")) }
    var social2 by remember { mutableStateOf(TextFieldValue("")) }
    var social3 by remember { mutableStateOf(TextFieldValue("")) }
    var logoLink by remember { mutableStateOf(TextFieldValue("")) }
    var showDescriptionDialog by remember { mutableStateOf(false) }
    var descriptionText by remember { mutableStateOf(TextFieldValue("")) }
    var showEmptyAlert by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = gradientColorsBlue,
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(lilGray),
            color = Color.Transparent
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Encabezado a todo ancho
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
                        text = stringResource(R.string.app_name),
                        fontSize = 64.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Título de la pantalla
                    Text(
                        text = stringResource(R.string.screenTitleInfo),
                        fontSize = 32.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 16.dp)
                    )

                    // Nombre de la Compañía
                    Text(
                        text = stringResource(R.string.etiqueta_nombre_compania),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = companyName,
                            onValueChange = { companyName = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Red Social 1
                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social1,
                            onValueChange = { social1 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Red Social 2
                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social2,
                            onValueChange = { social2 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Red Social 3
                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social3,
                            onValueChange = { social3 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Link del Logo
                    Text(
                        text = stringResource(R.string.link_logo),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = logoLink,
                            onValueChange = { logoLink = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para agregar descripción
                    Button(
                        onClick = { showDescriptionDialog = true },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp), clip = false),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = stringResource(R.string.Agregar_Descripcion),
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de confirmación (con validación)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(24.dp),
                                clip = false
                            )
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        royalBlue,
                                        deepSkyBlue,
                                        midnightBlue
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(1000f, 1000f)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                // Validación: companyName, logoLink y description no pueden quedar vacíos
                                // y al menos uno de los campos social1, social2 o social3 debe estar lleno
                                val nameOk = companyName.text.isNotBlank()
                                val logoOk = logoLink.text.isNotBlank()
                                val descOk = descriptionText.text.isNotBlank()
                                val atLeastOneSocial = social1.text.isNotBlank() ||
                                        social2.text.isNotBlank() ||
                                        social3.text.isNotBlank()

                                if (nameOk && logoOk && descOk && atLeastOneSocial) {
                                    usersViewModel.saveCompanyData(
                                        companyName.text,
                                        social1.text,
                                        social2.text,
                                        social3.text,
                                        logoLink.text,
                                        descriptionText.text
                                    )
                                    navController.navigate(NavRoutes.RegisterCredentialsScreen.ROUTE)
                                } else {
                                    showEmptyAlert = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.bTextConfirmar),
                            fontSize = 25.sp,
                            color = walterWhite,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Popup de descripción
            if (showDescriptionDialog) {
                AlertDialog(
                    onDismissRequest = { showDescriptionDialog = false },
                    title = { Text(text = stringResource(R.string.Descripcion)) },
                    text = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .padding(horizontal = 8.dp, vertical = 12.dp)
                        ) {
                            BasicTextField(
                                value = descriptionText,
                                onValueChange = { descriptionText = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                singleLine = false
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showDescriptionDialog = false }) {
                            Text(text = stringResource(R.string.etiqueta_ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDescriptionDialog = false }) {
                            Text(text = stringResource(R.string.Cancelar))
                        }
                    }
                )
            }

            // AlertDialog si faltan campos
            if (showEmptyAlert) {
                AlertDialog(
                    onDismissRequest = { showEmptyAlert = false },
                    title = { Text(text = stringResource(R.string.Alerta)) },
                    text = {
                        Text(
                            text = stringResource(R.string.alerta_alerta)
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showEmptyAlert = false }) {
                            Text(text = stringResource(R.string.etiqueta_ok))
                        }
                    }
                )
            }
        }
    }
}


