package com.example.oportunia.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.components.texAndLable
import com.example.oportunia.ui.theme.blackPanter
import com.example.oportunia.ui.theme.lilGray
import com.example.oportunia.ui.theme.lilRedMain
import com.example.oportunia.ui.theme.walterWhite
import com.example.oportunia.ui.viewmodel.StudentViewModel
import com.example.oportunia.ui.viewmodel.UsersViewModel
@Composable
fun RegisterOptionScreenPAndE(
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    navController: NavHostController
) {
    var correo by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var contraVali by remember { mutableStateOf("") }

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                        color = lilRedMain,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
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

            // Título
            Text(
                text = stringResource(R.string.screenTitleInfo),
                fontSize = 32.sp,
                color = blackPanter,
                modifier = Modifier.padding(top = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                texAndLable(
                    titulo = stringResource(R.string.emailL),
                    placeholder = stringResource(R.string.emailExample),
                    valor = correo,
                    alCambiarValor = { correo = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                texAndLable(
                    titulo = stringResource(R.string.passworL),
                    placeholder = "",
                    valor = contra,
                    alCambiarValor = { contra = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                texAndLable(
                    titulo = stringResource(R.string.passworC),
                    placeholder = "",
                    valor = contraVali,
                    alCambiarValor = { contraVali = it }
                )

                Spacer(modifier = Modifier.height(200.dp))

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
                            color = lilRedMain,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            when {
                                correo.isBlank() -> {
                                    alertMessage = context.getString(R.string.error_empty_email)
                                    showAlert = true
                                }
                                contra.isBlank() -> {
                                    alertMessage = context.getString(R.string.error_empty_password)
                                    showAlert = true
                                }
                                contra != contraVali -> {
                                    alertMessage = context.getString(R.string.error_password_mismatch)
                                    showAlert = true
                                }

                                else -> {
                                    studentViewModel.setCorreo(correo)
                                    studentViewModel.setContrasenna(contraVali)

                                    Log.d("StudentInfo", "Correo guardado: $correo")
                                    Log.d("StudentInfo", "Contraseña guardada: $contraVali")

                                    val userId = usersViewModel.getNextId()
                                    studentViewModel.saveStudent(userId)

                                    usersViewModel.saveUser(
                                        id = userId,
                                        email = studentViewModel.correo.value,
                                        password = studentViewModel.contrasenna.value,
                                        roleId = 3
                                    )

                                    navController.navigate(NavRoutes.Log.ROUTE) {
                                        popUpTo(0) { inclusive = true }
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

        // AlertDialog
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Error") },
                text = { Text(alertMessage) }
            )
        }
    }
}
