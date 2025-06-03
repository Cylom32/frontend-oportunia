package com.example.oportunia.presentation.ui.screens
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import com.example.oportunia.domain.model.MessageResponseS
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.window.DialogProperties


@Composable
fun SentRequestScreen(
    navController: NavHostController,
    userViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {

    val token by userViewModel.token.collectAsState()
    val studentId by studentViewModel.studentIdd.collectAsState()


    val messages by studentViewModel.messagesByStudent.collectAsState()


    LaunchedEffect(token, studentId) {
        if (!token.isNullOrBlank() && studentId != null) {
            studentViewModel.fetchMessagesByStudent(token!!, studentId!!)
        }
    }


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
                        text = stringResource(R.string.titulo_solicitudes_enviadas),
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


        selectedMessage?.let { msg ->
            AlertDialog(
                onDismissRequest = { selectedMessage = null },
                title = {
                    Text(text = stringResource(R.string.titulo_detalles_del_mensaje), fontWeight = FontWeight.Bold)
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
                        Text(text =  stringResource(R.string.etiqueta_fecha_envio))
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


            Text(
                text = partialDetail,
                modifier = Modifier
                    .weight(1f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(12.dp))


            Text(
                text = date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
