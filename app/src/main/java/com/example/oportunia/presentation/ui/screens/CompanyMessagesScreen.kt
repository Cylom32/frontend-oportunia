// CompanyMessagesScreen.kt
package com.example.oportunia.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.domain.model.MessageResponseC

@Composable
fun CompanyMessagesScreen(
    navController: NavHostController,
    companyViewModel: CompanyViewModel
) {
    val companyId by companyViewModel.companyIdC.collectAsState()
    val messages by companyViewModel.messagesC.collectAsState()

    LaunchedEffect(companyId) {
        if (companyId != null) {
            companyViewModel.fetchMessagesByCompany()
        }
    }

    var selectedMessage by remember { mutableStateOf<MessageResponseC?>(null) }
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
                // Banner idéntico al de SentRequestScreen
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
                        text = stringResource(R.string.titulo_solicitudes_enviadas),
                        color = walterWhite,
                        fontSize = (screenWidth.value * 0.07).sp
                    )
                }

                // Lista de mensajes de la compañía (sin logo)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        items = messages,
                        key = { message -> message.idMessage }
                    ) { message ->
                        // Card personalizada sin logo
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clickable { selectedMessage = message },
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
                                // Detalle parcial en el centro (sin imagen)
                                Text(
                                    text = message.detail
                                        .take(20)
                                        .let { if (message.detail.length > 20) "$it…" else it },
                                    modifier = Modifier.weight(1f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                // Fecha a la derecha
                                Text(
                                    text = message.sendDate.substring(0, 10),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        selectedMessage?.let { msg ->
            AlertDialog(
                onDismissRequest = { selectedMessage = null },
                title = {
                    Text(
                        text = stringResource(R.string.titulo_detalles_del_mensaje),
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.etiqueta_detalle_completo))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.detail,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = stringResource(R.string.etiqueta_nombre_archivo))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.file,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(msg.file))
                                    context.startActivity(intent)
                                },
                            fontSize = 14.sp,
                            color = Color(0xFF1E88E5)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = stringResource(R.string.etiqueta_fecha_envio))
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
                        Text(text = stringResource(R.string.boton_cerrar_dialogo))
                    }
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }
    }
}
