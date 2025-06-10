package com.example.oportunia.presentation.ui.screens

import androidx.compose.runtime.Composable
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel


@Composable
fun RequestScreen(
    navController: NavHostController,
    userViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth  = maxWidth


        val token by userViewModel.token.collectAsState(initial = null)
        val studentId by studentViewModel.studentIdd.collectAsState(initial = null)



        LaunchedEffect(token, studentId) {
            if (!token.isNullOrBlank() && studentId != null) {
                studentViewModel.fetchCvList(token!!, studentId!!)
            }
        }


        val cvList by studentViewModel.cvlista.collectAsState(initial = emptyList())

        var mensaje by remember { mutableStateOf("") }
        var selectedCvFile by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }


        LaunchedEffect(cvList) {
            if (cvList.isNotEmpty() && selectedCvFile.isBlank()) {
                selectedCvFile = cvList.first().file
            }
        }

        val context = LocalContext.current

        Scaffold(

        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = lilGray
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = screenWidth * 0.06f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.etiqueta_archivo_adjunto),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.07f)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .clickable { if (cvList.isNotEmpty()) showDialog = true }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = if (selectedCvFile.isBlank())
                                stringResource(R.string.etiqueta_seleccionar_cv)
                            else
                                cvList.find { it.file == selectedCvFile }?.name?.substringBeforeLast(".pdf") ?: selectedCvFile,
                            fontSize = (screenWidth.value * 0.045).sp,
                            color = if (cvList.isEmpty()) Color.Gray else Color.Black
                        )
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = stringResource(R.string.etiqueta_selecciona_cv)) },
                            text = {
                                Column {
                                    if (cvList.isEmpty()) {
                                        Text(
                                            text = stringResource(R.string.etiqueta_no_hay_cvs),
                                            fontSize = (screenWidth.value * 0.04).sp
                                        )
                                    } else {
                                        cvList.forEach { cv ->
                                            val displayName = cv.name.substringBeforeLast(".pdf")
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                RadioButton(
                                                    selected = (selectedCvFile == cv.file),
                                                    onClick = {
                                                        selectedCvFile = cv.file
                                                    }
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))






                                                Column(
                                                    modifier = Modifier
                                                        .clickable {
                                                            if (cv.file.isNotBlank()) {
                                                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                                                    data = Uri.parse(cv.file)
                                                                }
                                                                if (intent.resolveActivity(context.packageManager) != null) {
                                                                    context.startActivity(intent)
                                                                }
                                                            }
                                                            selectedCvFile = cv.file
                                                            showDialog = false
                                                        }
                                                ) {
                                                    Text(
                                                        text = displayName,
                                                        fontSize = (screenWidth.value * 0.04).sp,
                                                        color = Color.Black
                                                    )

                                                }


                                            }
                                        }
                                    }
                                }
                            },
                            confirmButton = { /* No se necesita botón extra */ },
                            dismissButton = { /* Al tocar fuera cierra */ }
                        )
                    }

                    Text(
                        text = stringResource(R.string.etiqueta_mensaje_adicional),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.25f)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        if (mensaje.isEmpty()) {
                            Text(
                                text = stringResource(R.string.etiqueta_escribe_mensaje),
                                fontSize = (screenWidth.value * 0.04).sp,
                                color = Color.Gray
                            )
                        }
                        BasicTextField(
                            value = mensaje,
                            onValueChange = { mensaje = it },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = (screenWidth.value * 0.04).sp,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Button(
                        onClick = {
                            // Antes de enviar, validamos que token, studentId y selectedCvFile estén listos
                            val rawToken = token ?: return@Button
                            val studId   = studentId ?: return@Button
                            val inboxId  = companyViewModel.inboxByCompany.value?.idInbox ?: return@Button

                            Log.d("SendMessageDebug", "rawToken: $rawToken")
                            Log.d("SendMessageDebug", "mensaje: $mensaje")
                            Log.d("SendMessageDebug", "selectedCvFile: $selectedCvFile")
                            Log.d("SendMessageDebug", "inboxId: $inboxId")
                            Log.d("SendMessageDebug", "studId: $studId")
                            companyViewModel.sendMessage(
                                rawToken = rawToken,
                                detail   = mensaje,
                                file     = selectedCvFile,
                                idInbox  = inboxId,
                                idStudent = studId
                            )

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = lilRed),
                        modifier = Modifier
                            .height(screenHeight * 0.07f)
                            .width(screenWidth * 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.ApplyButtonText),
                            color = Color.White,
                            fontSize = (screenWidth.value * 0.05).sp
                        )
                    }
                }
            }

            // ————————————————————————————————————————————————————
            // 4) Si el envío fue exitoso, mostramos un AlertDialog con OK
            // ————————————————————————————————————————————————————
            val sendSuccess by companyViewModel.sendSuccess.collectAsState()

            if (sendSuccess == true) {
                // Mostrar AlertDialog
                AlertDialog(
                    onDismissRequest = { /* Evita que el usuario lo cierre manualmente */ },
                    title = { Text(text = stringResource(R.string.etiqueta_exito_mensaje)) },
                    text = { Text(text = stringResource(R.string.etiqueta_enviado_exitosamente)) },
                    confirmButton = {
                        // No mostramos botón, navegación será automática
                    }
                )

                // Lanzar navegación después de un retardo
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(1000L) // Espera 2 segundos
                    companyViewModel.clearSendStatus()
                    navController.navigate(NavRoutes.HomeScreenS.ROUTE) {
                        popUpTo(NavRoutes.HomeScreenS.ROUTE) { inclusive = true }
                    }
                }
            }





        }
    }
}
