package com.example.oportunia.presentation.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.example.oportunia.R
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel






@Composable
fun EditUCVScreen(
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel
) {
    val context = LocalContext.current

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
    var newCvLink by remember { mutableStateOf("") }

    // Estados para detalles
    var selectedCv by remember { mutableStateOf<CVResponseS?>(null) }
    var showDetailsDialog by remember { mutableStateOf(false) }

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
            // Encabezado
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
                            label = { Text(text = stringResource(R.string.etiqueta_nombre_archivo)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        OutlinedTextField(
                            value = newCvLink,
                            onValueChange = { newCvLink = it },
                            label = { Text(text = stringResource(R.string.link_Archivo)) },
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
                            }
                        }
                        showAddDialog = false
                        newCvName = ""
                        newCvLink = ""
                    }) {
                        Text(text = stringResource(R.string.Guardar))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        newCvName = ""
                        newCvLink = ""
                    }) {
                        Text(text = stringResource(R.string.Cancelar))
                    }
                }
            )
        }

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