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
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun EditUCVScreen(
    studentViewModel: StudentViewModel
) {
    val context = LocalContext.current
    val cvs by studentViewModel.cvList.collectAsState()
    val studentState by studentViewModel.studentState.collectAsState()

    LaunchedEffect(Unit) {
        //studentViewModel.loadCvsBySelectedStudent()
       // studentViewModel.printStudentAndCvs()
    }

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

//                    studentViewModel.insertCv(localPath)
//                    studentViewModel.loadCvsBySelectedStudent()
                } catch (e: Exception) {
                    Log.e("PDF_COPY", "Error al copiar PDF: ${e.message}", e)
                }
            }
        }
    )

    Surface(
        modifier = Modifier.fillMaxSize().background(lilGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(lilGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
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
                Button(
                    onClick = {
                        pdfPickerLauncher.launch(arrayOf("application/pdf"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    modifier = Modifier.height(50.dp).width(200.dp)
                ) {
                    Text("Agregar CV", color = Color.Black, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Icono Agregar",
                        tint = mintGreen
                    )
                }

                cvs.forEach { cv ->
                    CVCard(
                        fileName = cv.name,
                        filePath = cv.file,
                        status = cv.status,
                        onDelete = {
                            //studentViewModel.deleteCv(cv)
                        },
                        onStatusChange = {
                            //studentViewModel.setCvAsActive(cv.id)
                        }
                    )
                }


            }
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
 //   var activo by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pdficon),
                contentDescription = "PDF",
                modifier = Modifier.size(48.dp).clickable {
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


fun getFileNameFromUri(context: Context, uri: Uri): String? {
    return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex != -1) {
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } else null
    }
}
