package com.example.oportunia.presentation.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import com.example.oportunia.domain.model.MessageResponseS
import java.io.File
import java.io.FileOutputStream
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.window.DialogProperties


@Composable
fun SentRequestScreen(
    navController: NavHostController,
    userViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel // aunque no lo usemos aquí para mensajes, lo dejamos en la firma
) {
    // 1) Recuperar token y studentId desde los ViewModels
    val token by userViewModel.token.collectAsState()
    val studentId by studentViewModel.studentIdd.collectAsState()

    // 2) Recuperar la lista de mensajes desde StudentViewModel
    val messages by studentViewModel.messagesByStudent.collectAsState()

    // 3) Disparar la petición en el ViewModel cuando tengamos token y studentId válidos
    LaunchedEffect(token, studentId) {
        if (!token.isNullOrBlank() && studentId != null) {
            studentViewModel.fetchMessagesByStudent(token!!, studentId!!)
        }
    }

    // 4) Estado para el mensaje seleccionado (para mostrar el popup)
    var selectedMessage by remember { mutableStateOf<MessageResponseS?>(null) }
    val context = LocalContext.current

    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

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
                Box(
                    modifier = Modifier
                        .height(screenHeight * 0.2f)
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
                    Text(
                        text = "Solicitudes enviadas",
                        color = walterWhite,
                        fontSize = (screenWidth.value * 0.07).sp
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    messages?.let { list ->
                        items(
                            items = list,
                            key = { message -> message.idMessage }
                        ) { message ->
                            // Mostramos solo detalle parcial y fecha en la tarjeta
                            InternshipCard(
                                partialDetail = message.detail
                                    .take(20)
                                    .let { if (message.detail.length > 20) "$it…" else it },
                                date = message.sendDate.substring(0, 10),
                                logoResId = R.drawable.world,
                                onCardClick = { selectedMessage = message }
                            )
                        }
                    }
                }
            }
        }

        // Si hay un mensaje seleccionado, mostramos un AlertDialog con sus detalles
        selectedMessage?.let { msg ->
            AlertDialog(
                onDismissRequest = { selectedMessage = null },
                title = {
                    Text(text = "Detalles del mensaje", fontWeight = FontWeight.Bold)
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "• Detalle completo:")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.detail,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "• Nombre de archivo:")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.file,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .clickable {
                                    // Abrir en navegador usando la URL real del archivo
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(msg.file))
                                    context.startActivity(intent)
                                },
                            fontSize = 14.sp,
                            color = Color(0xFF1E88E5) // azul para enlace
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "• Fecha de envío:")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.sendDate.substring(0, 10),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            fontSize = 14.sp
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedMessage = null }) {
                        Text(text = "Cerrar")
                    }
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }
    }
}


// InternshipCard.kt
@Composable
fun InternshipCard(
    partialDetail: String,
    date: String,
    logoResId: Int,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo a la izquierda
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Detalle parcial en el centro (uso weight = 1f para que ocupe el espacio restante)
            Text(
                text = partialDetail,
                modifier = Modifier
                    .weight(1f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Fecha a la derecha
            Text(
                text = date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
