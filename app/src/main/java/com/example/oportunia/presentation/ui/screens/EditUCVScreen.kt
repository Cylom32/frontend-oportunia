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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.oportunia.R             // <-- Nuevo DTO
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel   // <-- Renombrado a UsersViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun EditUCVScreen(
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel    // <-- Mantener nombre usersViewModel
) {
    val context = LocalContext.current

    // 1) Token y studentId
    val token by usersViewModel.token.collectAsState()                   // <-- usersViewModel.token
    val studentId by studentViewModel.studentIdd.collectAsState()        // <-- studentIdd

    // 2) Estado de Student y lista de CVs (ahora cvlistaa)
    val studentState by studentViewModel.studentState.collectAsState()
    val cvs by studentViewModel.cvlistaa.collectAsState()                // <-- cvlistaa

    // 3) Estados para manejar el popup
    var selectedCv by remember { mutableStateOf<CVResponseS?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // 4) Al iniciar la pantalla, si tenemos token y studentId, cargar CVs
    LaunchedEffect(token, studentId) {
        if (!token.isNullOrBlank() && studentId != null) {
            studentViewModel.fetchCvLista(token!!)
        }
    }

    // 5) Selector de PDF (se mantiene igual)
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                try {
                    val fileName = getFileNameFromUri(context, it)
                        ?: "cv_${System.currentTimeMillis()}.pdf"
                    val file = File(context.filesDir, fileName)

                    val inputStream = context.contentResolver.openInputStream(it)
                    val outputStream = FileOutputStream(file)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()

                    val localPath = file.absolutePath
                    Log.d("PDF_COPY", "Archivo copiado a: $localPath")

                    // studentViewModel.insertCv(localPath)
                    // studentViewModel.loadCvsBySelectedStudent()
                } catch (e: Exception) {
                    Log.e("PDF_COPY", "Error al copiar PDF: ${e.message}", e)
                }
            }
        }
    )

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
                            text = "Cargando...",
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
                text = "Edición de CV",
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
                // ——— BOTÓN PARA AGREGAR UN CV ———
                Button(
                    onClick = {
                        pdfPickerLauncher.launch(arrayOf("application/pdf"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                ) {
                    Text("Agregar CV", color = Color.Black, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Icono Agregar",
                        tint = mintGreen
                    )
                }

                // ——— LISTA DE CVs: cada tarjeta es clickeable para abrir popup ———
                cvs.forEach { cv ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCv = cv
                                showDialog = true
                            }
                    ) {
                        CVCard(
                            fileName = cv.name,
                            filePath = cv.file,
                            status = true,          // <-- Asignar un valor fijo (true) ya que CVResponseS no tiene "status"
                            onDelete = {
                                // studentViewModel.deleteCv(cv)
                            },
                            onStatusChange = {
                                // studentViewModel.setCvAsActive(cv.idCv)
                            }
                        )
                    }
                }
            }
        }

        // ——— ALERTDIALOG: muestra toda la info del CV seleccionado ———
        if (showDialog && selectedCv != null) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    selectedCv = null
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        selectedCv = null
                    }) {
                        Text(text = "Cerrar")
                    }
                },
                title = {
                    Text(text = "Detalles del CV")
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "ID CV: ${selectedCv!!.idCv}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Nombre: ${selectedCv!!.name}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Ruta de archivo:")
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

// Helper: obtiene nombre de archivo desde URI, se mantiene igual
private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index >= 0) {
                name = it.getString(index)
            }
        }
    }
    return name
}

// CVCard se deja intacto, solo usa los parámetros pasados arriba
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
                Text(
                    text = if (status) "Activo" else "Inactivo",
                    color = if (status) Color(0xFF4CAF50) else Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        onStatusChange()
                    }
                )
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
