// src/main/java/com/example/oportunia/presentation/ui/screens/CompanyInfoScreenS.kt
package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun CompanyInfoScreenS(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {
    val token by usersViewModel.token.collectAsState(initial = null)
    val companyId by companyViewModel.selectedCompanyId.collectAsState(initial = null)
    val company by companyViewModel.companyWithNetworks.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = NavRoutes.CompanyInfoScreenS.ROUTE,
                onScreenSelected = { route ->
                    if (route != NavRoutes.CompanyInfoScreenS.ROUTE) {
                        navController.navigate(route)
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(lilGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header degradado con solo el nombre de la compañía
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                            clip = false
                        )
                        .gradientBackgroundBlue(
                            gradientColorsBlue,
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val companyName by companyViewModel.companyName.collectAsState()
                    Text(
                        text = companyName.orEmpty(),
                        color = walterWhite,
                        fontSize = 29.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Card con logo, nombre, descripción y redes sociales
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        company?.let { it ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val logoUrl by companyViewModel.companyLogo.collectAsState()
                                if (!logoUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = logoUrl,
                                        contentDescription = "Logo empresa",
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.shakehands1062821),
                                        contentDescription = "Logo placeholder",
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = it.companyName,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = it.companyDescription ?: "",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val uriHandler = LocalUriHandler.current
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                it.socialNetworks.forEach { sn ->
                                    val host = Uri.parse(sn.link)
                                        .host
                                        .orEmpty()
                                    val domain = host
                                        .split('.')
                                        .let { parts ->
                                            if (parts.size >= 2) parts[parts.size - 2] else parts.first()
                                        }
                                    val label = domain.replaceFirstChar { char -> char.uppercase() }

                                    Text(
                                        text = label,
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .clickable { uriHandler.openUri(sn.link) },
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            textDecoration = TextDecoration.Underline
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de pasantías situado abajo y centrado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            val authToken = token ?: return@Button
                            val id = companyId ?: return@Button
                            companyViewModel.fetchPublicationsByCompany(
                                authToken = authToken,
                                companyIdParam = id
                            )
                            Log.d("antes de viajar", id.toString())
                            navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE)
                            Log.d("despues de viajar", id.toString())
                        },
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                clip = false
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.etiqueta_pasantias),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
