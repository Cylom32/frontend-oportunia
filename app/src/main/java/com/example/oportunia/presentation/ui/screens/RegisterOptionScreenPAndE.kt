package com.example.oportunia.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.components.texAndLable
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import androidx.compose.runtime.rememberCoroutineScope
import com.example.oportunia.presentation.ui.theme.walterWhite
import kotlinx.coroutines.launch


import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.TextFieldValue



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff




@Composable
fun RegisterOptionScreenPAndE(
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    var correo by remember { mutableStateOf("") }

    // Cambiamos contra y contraVali a TextFieldValue para usar BasicTextField
    var contra by remember { mutableStateOf(TextFieldValue("")) }
    var contraVali by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColorsBlue,
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            color = Color.Transparent
        ) {}
    }

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

            Text(
                text = stringResource(R.string.screenTitleInfo),
                fontSize = 32.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Correo (sin cambios)
                Text(
                    text = stringResource(R.string.emailL),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = true
                        )
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    BasicTextField(
                        value = TextFieldValue(correo),
                        onValueChange = { correo = it.text },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contraseña con toggle (igual que en pantalla de registro anterior)
                Text(
                    text = stringResource(R.string.passworL),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = true
                        )
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp)
                ) {
                    BasicTextField(
                        value = contra,
                        onValueChange = { contra = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 32.dp), // espacio para el icono
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

                Spacer(modifier = Modifier.height(16.dp))

                // Confirmar Contraseña (siempre oculta)
                Text(
                    text = stringResource(R.string.passworC),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = true
                        )
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    BasicTextField(
                        value = contraVali,
                        onValueChange = { contraVali = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                Spacer(modifier = Modifier.height(150.dp))

                Box(
                    modifier = Modifier
                        .width(350.dp)
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
                            when {
                                correo.isBlank() -> {
                                    alertMessage = "El correo no puede estar vacío"
                                    showAlert = true
                                }
                                contra.text.isBlank() -> {
                                    alertMessage = "La contraseña no puede estar vacía"
                                    showAlert = true
                                }
                                contra.text != contraVali.text -> {
                                    alertMessage = "Las contraseñas no coinciden"
                                    showAlert = true
                                }
                                else -> {
                                    scope.launch {
                                        usersViewModel.fetchUserByEmail(correo) { idUser ->
                                            if (idUser == null) {
                                                usersViewModel.setEmail(correo)
                                                usersViewModel.setPassword(contraVali.text)

                                                Log.d("Registro", "Contraseña a enviar: ${contraVali.text}")

                                                usersViewModel.createUserWithoutId(
                                                    rawEmail    = correo,
                                                    rawPassword = contraVali.text
                                                )

                                                scope.launch {
                                                    usersViewModel.fetchUserByEmail(correo) { fetchedId ->
                                                        fetchedId?.let { idUser ->
                                                            Log.d("Registro", "ID de usuario obtenido: $idUser")

                                                            val nombre = usersViewModel.nombre.value
                                                            val primerApellido = usersViewModel.primerApellido.value
                                                            val segundoApellido = usersViewModel.segundoApellido.value
                                                            val uniId: Int = usersViewModel.selectedUniversityId.value

                                                            studentViewModel.createStudentWithoutId(
                                                                userId       = idUser,
                                                                rawFirstName = nombre,
                                                                rawLastName1 = primerApellido,
                                                                rawLastName2 = segundoApellido,
                                                                universityId = uniId
                                                            )

                                                            navController.navigate(NavRoutes.Log.ROUTE) {
                                                                popUpTo(0) { inclusive = true }
                                                            }
                                                        } ?: run {
                                                            Log.e("Registro", "No se obtuvo ID de usuario; no se crea estudiante")
                                                        }
                                                    }
                                                }

                                                navController.navigate(NavRoutes.Log.ROUTE) {
                                                    popUpTo(0) { inclusive = true }
                                                }
                                            } else {
                                                alertMessage = "Ese correo ya está asociado"
                                                showAlert = true
                                            }
                                        }
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
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(stringResource(R.string.acceptText))
                    }
                },
                title = { Text(stringResource(R.string.error)) },
                text = { Text(alertMessage) }
            )
        }
    }
}