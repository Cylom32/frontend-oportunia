package com.example.oportunia.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import java.io.File
import java.io.FileOutputStream


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
        val sendSuccess by companyViewModel.sendSuccess.collectAsState()     // <-- Estado del envío

        val cvList by studentViewModel.cvlista.collectAsState()

        var mensaje by remember { mutableStateOf("") }
        var selectedCvFile by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }

        // ② Cuando cvList cambie, precargamos el primer cv.file (si existe)
        LaunchedEffect(cvList) {
            if (cvList.isNotEmpty() && selectedCvFile.isBlank()) {
                selectedCvFile = cvList.first().file
            }
        }

        // ③ Contexto necesario para lanzar el Intent
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
                        text = "Archivo Adjunto",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // ─── CAJA QUE MUESTRA EL CV SELECCIONADO y abre el popup ──────────────────
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
                            text = if (selectedCvFile.isBlank()) "Seleccionar CV" else selectedCvFile,
                            fontSize = (screenWidth.value * 0.045).sp,
                            color = if (cvList.isEmpty()) Color.Gray else Color.Black
                        )
                    }

                    // ─── DIALOGO PARA SELECCIONAR UN CV Y ABRIRLO EN NAVEGADOR ───────────────
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = "Selecciona un CV") },
                            text = {
                                Column {
                                    if (cvList.isEmpty()) {
                                        Text(
                                            text = "No hay CVs disponibles.",
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
                                                Text(
                                                    text = displayName,
                                                    fontSize = (screenWidth.value * 0.04).sp,
                                                    modifier = Modifier
                                                        .clickable {
                                                            Intent(Intent.ACTION_VIEW).also { intent ->
                                                                intent.data = Uri.parse(cv.file)
                                                                context.startActivity(intent)
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
                        text = "Mensaje Adicional",
                        style = MaterialTheme.typography.bodyLarge,
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
                                    text = "Escribe tu mensaje aquí…",
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
                            text = "Aplicar",
                            color = Color.White,
                            fontSize = (screenWidth.value * 0.05).sp
                        )
                    }
                }
            }

            // ─── ALERTDIALOG DE CONFIRMACIÓN ────────────────────────────────────────────
            if (sendSuccess == true) {
                AlertDialog(
                    onDismissRequest = {
                        companyViewModel.clearSendStatus()
                        navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE) // Ajusta la ruta deseada
                    },
                    title = { Text(text = "Éxito") },
                    text = { Text(text = "Enviado exitosamente") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                companyViewModel.clearSendStatus()
                                navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE)
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
            // ───────────────────────────────────────────────────────────────────────────────
        }
    }
}
