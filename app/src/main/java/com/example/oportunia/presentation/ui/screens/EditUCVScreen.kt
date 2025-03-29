package com.example.oportunia.presentation.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.components.texAndLable
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.mintGreen
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel



@Preview
@Composable
fun EditUCVScreen() {
    val context = LocalContext.current
    var activo by remember { mutableStateOf(true) }
    var selectedPdfUri by remember { mutableStateOf<String?>(null) }

    // Launcher para seleccionar PDF
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                val path = it.toString()
                selectedPdfUri = path
                Log.d("PDF_URI", "Ruta del PDF: $path")
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
                        brush = Brush.linearGradient(
                            colors = listOf(royalBlue, deepSkyBlue, midnightBlue),
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f)
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Wilson Munoz",
                    fontSize = 32.sp,
                    color = walterWhite,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }

            Text(
                text = "Edición de CV",
                fontSize = 32.sp,
                color = com.example.oportunia.presentation.ui.theme.blackPanter,
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

                // BOTÓN QUE ABRE ARCHIVOS
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

                CVCard("Hola que tal")
                CVCard("Hola que tal")
                CVCard("Hola que tal")

                // Si se seleccionó un PDF, lo mostramos
                selectedPdfUri?.let {
                    Text(
                        text = "Seleccionado: $it",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CVCard(
    fileName: String = "CV Jhon Doe1.pdf"
) {
    var activo by remember { mutableStateOf(true) }

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
            // Icono PDF
            Icon(
                painter = painterResource(id = R.drawable.pdficon),
                contentDescription = "PDF",
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Texto y estado
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fileName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (activo) "Activo" else "Inactivo",
                    color = if (activo) Color(0xFF4CAF50) else Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        // aquí van las acciones prro
                        activo = !activo
                    }
                )
            }

            // Icono de eliminar
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = Color.Red,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        // Acción de eliminar
                    }
            )
        }
    }
}





