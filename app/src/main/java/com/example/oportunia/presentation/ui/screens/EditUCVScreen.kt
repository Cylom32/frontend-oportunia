package com.example.oportunia.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.oportunia.R
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import java.io.File

@Composable
fun EditUCVScreen(
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel
) {
    val context = LocalContext.current

    // 1) Token y studentId
    val token by usersViewModel.token.collectAsState()
    val studentId by studentViewModel.studentIdd.collectAsState()

    // 2) Estado de Student y lista de CVs (cvlistaa)
    val studentState by studentViewModel.studentState.collectAsState()
    val cvs by studentViewModel.cvlistaa.collectAsState()
    val deleteResult by studentViewModel.deleteResult.collectAsState()

    // 3) Estados para mostrar el dialog de “Agregar CV”
    var showAddDialog by remember { mutableStateOf(false) }
    var newCvName by remember { mutableStateOf("") }
    var newCvLink by remember { mutableStateOf("") }

    // 4) Estados para manejar el popup de detalles
    var selectedCv by remember { mutableStateOf<CVResponseS?>(null) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    // 5) Cargar lista al iniciar la pantalla
    LaunchedEffect(token, studentId) {
        if (!token.isNullOrBlank() && studentId != null) {
            studentViewModel.fetchCvLista(token!!)
        }
    }

    // 6) Cuando deleteResult == true, recargar lista y resetear
    LaunchedEffect(deleteResult) {
        if (deleteResult == true && !token.isNullOrBlank()) {
            studentViewModel.fetchCvLista(token!!)
            studentViewModel.resetDeleteResult()
        }
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
            // ——— ENCABEZADO CON DATOS DEL ESTUDIANTE ———
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(royalBlue, deepSkyBlue, midnightBlue),
                            start = Offset.Zero,
                            end = Offset(1000f, 1000f)
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (studentState) {
                    is StudentState.Loading -> {
                        Text(
                            text = "Cargando…",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    is StudentState.Success -> {
                        val student = (studentState as StudentState.Success).student
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${student.name} ${student.lastName1}",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    is StudentState.Error -> {
                        val message = (studentState as StudentState.Error).message
                        Text(
                            text = "Error: $message",
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    StudentState.Empty -> {
                        Text(
                            text = "Sin datos de estudiante",
                            color = Color.LightGray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.titulo_edit_cv),
                fontSize = 32.sp,
                color = blackPanter,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Condición: solo mostrar o habilitar el botón si hay ≤ 2 CVs
                val canAddCv = cvs.size <= 2

                Button(
                    onClick = {
                        if (canAddCv) {
                            showAddDialog = true
                        }
                    },
                    enabled = canAddCv,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canAddCv) Color.White else Color.LightGray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                ) {
                    Text(
                        text = stringResource(R.string.boton_agregar_cv),
                        color = if (canAddCv) Color.Black else Color.DarkGray,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Icono Agregar",
                        tint = if (canAddCv) mintGreen else Color.Gray
                    )
                }

                if (!canAddCv) {
                    Text(
                        text = "Has alcanzado el máximo de CVs (3)",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // ——— LISTA DE CVs SCROLLEABLE ———
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(cvs) { cv ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCv = cv
                                    showDetailsDialog = true
                                }
                        ) {
                            CVCard(
                                fileName = cv.name,
                                filePath = cv.file,
                                status = true,
                                onDelete = {
                                    token?.let { t ->
                                        studentViewModel.deleteCv(t, cv.idCv)
                                    }
                                },
                                onStatusChange = { /* … */ }
                            )
                        }
                    }
                }
            }
        }

        // ——— DIALOG “AGREGAR CV” ———
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog = false
                    newCvName = ""
                    newCvLink = ""
                },
                title = {
                    Text(text = stringResource(R.string.boton_agregar_cv))
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newCvName,
                            onValueChange = { newCvName = it },
                            label = { Text(text = "Nombre del archivo") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        OutlinedTextField(
                            value = newCvLink,
                            onValueChange = { newCvLink = it },
                            label = { Text(text = "Link del archivo") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (!newCvName.isBlank() && !newCvLink.isBlank() && !token.isNullOrBlank()) {
                            studentId?.let { idEst ->
                                studentViewModel.createCv(
                                    token = token!!,
                                    name = newCvName,
                                    file = newCvLink,
                                    studentId = idEst
                                )
                                studentViewModel.fetchCvLista(token!!)
                            }
                        }
                        showAddDialog = false
                        newCvName = ""
                        newCvLink = ""
                    }) {
                        Text(text = "Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        newCvName = ""
                        newCvLink = ""
                    }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }

        // ——— DIALOG DETALLES DEL CV SELECCIONADO ———
        if (showDetailsDialog && selectedCv != null) {
            AlertDialog(
                onDismissRequest = {
                    showDetailsDialog = false
                    selectedCv = null
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDetailsDialog = false
                        selectedCv = null
                    }) {
                        Text(text = stringResource(R.string.boton_cerrar_dialogo))
                    }
                },
                title = {
                    Text(text = stringResource(R.string.etiqueta_detalles_del_cv))
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.etiqueta_id_cv) + " ${selectedCv!!.idCv}",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.etiqueta_nombre_archivo_cv) + ": ${selectedCv!!.name}"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.etiqueta_ruta_archivo))
                        Text(
                            text = selectedCv!!.file,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun CVCard(
    fileName: String,
    filePath: String,
    status: Boolean,
    onDelete: () -> Unit,
    onStatusChange: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pdficon),
                contentDescription = "PDF",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        val file = File(filePath)
                        if (!file.exists()) {
                            Log.e("CVCard", "Archivo no encontrado: $filePath")
                            return@clickable
                        }
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(uri, "application/pdf")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e("CVCard", "No se pudo abrir el PDF: ${e.message}", e)
                        }
                    },
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fileName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = Color.Red,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onDelete()
                    }
            )
        }
    }
}
