package com.example.oportunia.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel








@Composable
fun CompanyInfoScreenForCompany(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {
    val companyId by companyViewModel.companyIdC.collectAsState()
    val imgUrl by companyViewModel.imgShow.collectAsState(initial = null)

    // Cuando companyId cambie (por ejemplo, se establezca en la VM), dispara la carga
    LaunchedEffect(companyId) {
        companyId?.let { id ->
            companyViewModel.fetchCompanyWithNetworksk(id)
        }
    }

    val token by usersViewModel.token.collectAsState(initial = null)
    val company by companyViewModel.companyWithNetworksk.collectAsState()
    val companyName: String = companyViewModel.companyNamek
        .collectAsState(initial = null)
        .value
        ?: ""

    val companyDescription: String = companyViewModel.companyDescriptionk
        .collectAsState(initial = null)
        .value
        ?: ""
    val socialNetworks by companyViewModel.socialNetworksk.collectAsState(initial = emptyList())

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = companyName,
                            color = walterWhite,
                            fontSize = 29.sp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                val authToken = token ?: return@Button
                                navController.navigate(NavRoutes.GridPublicationsCompany.ROUTE)
                               // navController.navigate(NavRoutes.EditInformationCompanyScreen.ROUTE)



                                /*


                                EditInformationCompanyScreen

                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa
                                laksjdalsdnaodsnasdkandksa





                                 */



                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = stringResource(R.string.etiqueta_pasantias), color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = imgUrl ?: "",
                                contentDescription = "Company logo",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = companyName,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = companyDescription,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val uriHandler = LocalUriHandler.current

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            socialNetworks.forEach { sn ->
                                val host = Uri.parse(sn.link)
                                    .host
                                    .orEmpty()
                                val domain = host
                                    .split('.')
                                    .let { parts ->
                                        if (parts.size >= 2) parts[parts.size - 2] else parts.first()
                                    }
                                val label = domain.replaceFirstChar { it.uppercase() }

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
        }
    }
}