package com.example.oportunia.presentation.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
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
import androidx.compose.material.icons.filled.Upload
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.presentation.ui.cloudinary.CloudinaryService
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@Composable
fun EditUCVScreen(
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Token y studentId
    val token by usersViewModel.token.collectAsState()
    val studentId by studentViewModel.studentIdd.collectAsState()

    // Estado de Student y lista de CVs
    val studentState by studentViewModel.studentState.collectAsState()
    val cvs by studentViewModel.cvlistaa.collectAsState()
    val deleteResult by studentViewModel.deleteResult.collectAsState()

    // Estados para agregar CV
    var showAddDialog by remember { mutableStateOf(false) }
    var newCvName by remember { mutableStateOf("") }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    // Estados para detalles
    var selectedCv by remember { mutableStateOf<CVResponseS?>(null) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    // Inicializar Cloudinary Service
    val cloudinaryService = remember {
        CloudinaryService(
            cloudName = "dfffvf0m6", // Reemplaza con tu cloud name
            uploadPreset = "mi_preset" // Reemplaza con tu upload preset
        )
    }

    // Launcher para seleccionar archivos PDF
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedPdfUri = it
            // Obtener el nombre del archivo
            val cursor = context.contentResolver.query(it, null, null, null, null)
            cursor?.use { c ->
                if (c.moveToFirst()) {
                    val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        selectedFileName = c.getString(nameIndex) ?: "documento.pdf"
                        // Si el nombre del CV está vacío, usar el nombre del archivo sin extensión
                        if (newCvName.isBlank()) {
                            newCvName = selectedFileName.substringBeforeLast(".")
                        }
                    }
                }
            }
        }
    }

    // Cargar lista inicial
    LaunchedEffect(token, studentId) {
        if (!token.isNullOrBlank() && studentId != null) {
            studentViewModel.fetchCvLista(token!!)
        }
    }

    // Recargar después de delete
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
            // Encabezado (mismo código anterior)
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
                            text = stringResource(R.string.cargando),
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
                            text = stringResource(R.string.sin_datos),
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
                        .padding(horizontal = 8.dp)
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
                        text = stringResource(R.string.max_cvs),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

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
                                    // showDetailsDialog = true
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
                                onStatusChange = {
                                    // Sin uso por ahora
                                }
                            )
                        }
                    }
                }
            }
        }

        // Diálogo mejorado para agregar CV
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog = false
                    newCvName = ""
                    selectedPdfUri = null
                    selectedFileName = ""
                    uploadError = null
                },
                title = {
                    Text(
                        text = stringResource(R.string.boton_agregar_cv),
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newCvName,
                            onValueChange = { newCvName = it },
                            label = { Text(text = stringResource(R.string.etiqueta_nombre_archivo)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            enabled = !isUploading
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botón para seleccionar archivo PDF
                        Button(
                            onClick = {
                                pdfPickerLauncher.launch("application/pdf")
                            },
                            enabled = !isUploading,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = mintGreen)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Upload,
                                contentDescription = "Seleccionar PDF"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (selectedFileName.isNotEmpty())
                                    "Archivo: $selectedFileName"
                                else
                                    "Seleccionar PDF"
                            )
                        }

                        // Mostrar progreso de subida
                        if (isUploading) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = mintGreen
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Subiendo archivo...",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        // Mostrar error si ocurre
                        uploadError?.let { error ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Error: $error",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (!newCvName.isBlank() && selectedPdfUri != null && !token.isNullOrBlank()) {
                                coroutineScope.launch {
                                    isUploading = true
                                    uploadError = null

                                    try {
                                        // Convertir URI a File temporal
                                        val inputStream = context.contentResolver.openInputStream(selectedPdfUri!!)
                                        val tempFile = File(context.cacheDir, selectedFileName)

                                        inputStream?.use { input ->
                                            FileOutputStream(tempFile).use { output ->
                                                input.copyTo(output)
                                            }
                                        }

                                        // Subir a Cloudinary
                                        val cloudinaryUrl = cloudinaryService.uploadPdf(tempFile)

                                        if (cloudinaryUrl != null) {
                                            // Crear CV con la URL de Cloudinary
                                            studentId?.let { idEst ->
                                                studentViewModel.createCv(
                                                    token = token!!,
                                                    name = newCvName,
                                                    file = cloudinaryUrl,
                                                    studentId = idEst
                                                )
                                            }

                                            // Limpiar archivo temporal
                                            tempFile.delete()

                                            // Cerrar diálogo
                                            showAddDialog = false
                                            newCvName = ""
                                            selectedPdfUri = null
                                            selectedFileName = ""
                                        } else {
                                            uploadError = "Error al subir el archivo"
                                        }
                                    } catch (e: Exception) {
                                        uploadError = "Error: ${e.message}"
                                    } finally {
                                        isUploading = false
                                    }
                                }
                            }
                        },
                        enabled = !newCvName.isBlank() && selectedPdfUri != null && !isUploading
                    ) {
                        Text(text = stringResource(R.string.Guardar))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAddDialog = false
                            newCvName = ""
                            selectedPdfUri = null
                            selectedFileName = ""
                            uploadError = null
                        },
                        enabled = !isUploading
                    ) {
                        Text(text = stringResource(R.string.Cancelar))
                    }
                }
            )
        }

        // Diálogo de detalles (mismo código anterior)
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.etiqueta_fecha_envio))
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            )
        }
    }
}

// CVCard component permanece igual
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
                    .size(48.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fileName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = filePath,
                    fontSize = 14.sp,
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.clickable {
                        val uri = Uri.parse(filePath)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            // Si no hay Activity para manejar el intent, ignorar
                        }
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