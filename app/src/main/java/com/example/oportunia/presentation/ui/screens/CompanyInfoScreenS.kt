package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import java.io.File
import java.io.FileOutputStream
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ExperimentalMaterialApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage


import com.example.oportunia.presentation.ui.screens.BottomNavigationBar
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel

@Composable
fun CompanyInfoScreenS(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {


    val token by usersViewModel
        .token
        .collectAsState(initial = null)
    val companyId by companyViewModel
        .selectedCompanyId
        .collectAsState(initial = null)

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
                // Header degradado
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


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = companyName.orEmpty(),
                            color = walterWhite,
                            fontSize = 29.sp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {


                                val authToken = token ?: return@Button
                                val id = companyId ?: return@Button


                                companyViewModel.fetchPublicationsByCompany(
                                    authToken = authToken,
                                    companyIdParam = id
                                )

                                navController.navigate(NavRoutes.GridPublicationsScreenS.ROUTE)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "Pasantías", color = Color.Black)


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
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            company?.let {
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
                                    text = it.companyDescription ?:"",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }


                            val uriHandler = LocalUriHandler.current



                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                company?.socialNetworks
                                    ?.forEach { sn ->
                                        // Extrae host (“www.facebook.com” → “facebook”)
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
}