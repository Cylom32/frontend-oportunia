// RegisterCredentialsScreen.kt
package com.example.oportunia.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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
fun RegisterCredentialsScreen(navController: NavHostController, usersViewModel: UsersViewModel) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                // Contenido desplazable
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Email
                    Text(
                        text = stringResource(R.string.emailL),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            )
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Contraseña (con opción mostrar/ocultar)
                    Text(
                        text = stringResource(R.string.passworL),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 12.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            )
                            .background(Color.White)
                            .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp)
                    ) {
                        BasicTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 32.dp), // dejar espacio para el icono
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                        )
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                tint = Color.Gray
                            )
                        }
                    }

                    // Confirmar Contraseña (siempre oculta)
                    Text(
                        text = stringResource(R.string.passworC),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 12.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            )
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Botón de confirmación al fondo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 60.dp, end = 60.dp, bottom = 24.dp)
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
                            // Validar que ambas contraseñas coincidan
                            if (password.text != confirmPassword.text) {
                                errorMessage = "Las contraseñas no coinciden."
                                showErrorDialog = true
                            } else {
                                usersViewModel.fetchUserByEmail(email.text) { userId ->
                                    if (userId != null) {
                                        errorMessage = "Este email ya está asociado a otra cuenta."
                                        showErrorDialog = true
                                    } else {
                                        usersViewModel.saveCredentials(
                                            email.text,
                                            password.text,
                                            confirmPassword.text
                                        )
                                        usersViewModel.registerCompanySaveCompanyAndInbox()
                                        Log.d("Registro", "Todo bien")
                                        navController.navigate(NavRoutes.Log.ROUTE)
                                    }
                                }
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
            }

            // Diálogo de error si las contraseñas no coinciden
            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text(text = stringResource(R.string.error)) },
                    text = { Text(text = errorMessage) },
                    confirmButton = {
                        TextButton(onClick = { showErrorDialog = false }) {
                            Text(text = stringResource(R.string.etiqueta_ok))
                        }
                    }
                )
            }
        }
    }
}