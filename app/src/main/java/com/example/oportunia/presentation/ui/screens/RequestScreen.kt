package com.example.oportunia.presentation.ui.screens

import androidx.compose.runtime.Composable
import android.content.Intent
import android.net.Uri
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
        val screenWidth = maxWidth

        val token by userViewModel.token.collectAsState()
        val inboxResult by companyViewModel.inboxByCompany.collectAsState()
        val studentId by studentViewModel.studentIdd.collectAsState()
        val sendSuccess by companyViewModel.sendSuccess.collectAsState()

        val cvList by studentViewModel.cvlista.collectAsState()

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
            bottomBar = {
                BottomNavigationBar(
                    selectedScreen = NavRoutes.RequestScreen.ROUTE,
                    onScreenSelected = { route ->
                        navController.navigate(route)
                    }
                )
            }
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
                        color =  Color.Black,
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
                            text = if (selectedCvFile.isBlank()) stringResource(R.string.etiqueta_seleccionar_cv) else selectedCvFile,
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
                                            val displayName = cv.file.substringBeforeLast(".pdf")
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
                                                // Dentro de tu AlertDialog, reemplaza el Text clickable por este bloque:
                                                Text(
                                                    text = displayName,
                                                    fontSize = (screenWidth.value * 0.04).sp,
                                                    modifier = Modifier
                                                        .clickable {
                                                            if (cv.file.isNotBlank()) {
                                                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                                                    data = Uri.parse(cv.file)
                                                                }
                                                                // Verifica que exista una actividad capaz de manejar el Intent
                                                                if (intent.resolveActivity(context.packageManager) != null) {
                                                                    context.startActivity(intent)
                                                                }
                                                            }
                                                            showDialog = false
                                                            selectedCvFile = cv.file
                                                        }
                                                        .padding(start = 4.dp)
                                                )

                                            }
                                        }
                                    }
                                }
                            },
                            confirmButton = { /* No hace falta botón extra */ },
                            dismissButton = { /* Se cierra al tocar fuera */ }
                        )
                    }
                    // ────────────────────────────────────────────────────────────────────────────────

                    Text(
                        text = stringResource(R.string.etiqueta_mensaje_adicional),
                        style = MaterialTheme.typography.bodyLarge,
                        color= Color.Black,//<-- Lo nuevo
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.25f)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
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
                    }

                    Button(
                        onClick = {
                            val rawToken = token ?: return@Button
                            val inboxId = inboxResult?.idInbox ?: return@Button
                            val studId = studentId ?: return@Button

                            companyViewModel.sendMessage(
                                rawToken = rawToken,
                                detail = mensaje,
                                file = selectedCvFile,
                                idInbox = inboxId,
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


            if (sendSuccess == true) {
                AlertDialog(
                    onDismissRequest = {
                        companyViewModel.clearSendStatus()
                        navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE) // Ajusta la ruta deseada
                    },
                    title = { Text(text = stringResource(R.string.etiqueta_exito_mensaje)) },
                    text = { Text(text = stringResource(R.string.etiqueta_enviado_exitosamente)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                companyViewModel.clearSendStatus()
                                navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE)
                            }
                        ) {
                            Text(stringResource(R.string.etiqueta_ok))
                        }
                    }
                )
            }
        }
    }
}
