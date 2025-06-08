package com.example.oportunia.presentation.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import coil.compose.AsyncImage
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel





@Composable
fun GridPublicationsScreenS(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel
) {


    val companyIdNullable by companyViewModel.selectedCompanyId.collectAsState(initial = null)

    // 2. Observar el inbox (InboxResult?) desde tu ViewModel
    val inboxResult by companyViewModel.inboxByCompany.collectAsState(initial = null)

    // 3. Cuando cambie el companyId, lanzar la petición para llenar el inbox
    LaunchedEffect(companyIdNullable) {
        companyIdNullable?.let { companyViewModel.fetchInboxByCompany(it) }
    }


    LaunchedEffect(inboxResult) {
        inboxResult?.let { Log.d("CompanyScreen", "Inbox for company: $it") }
    }





    val publications by companyViewModel.companyPublications.collectAsState(initial = emptyList())



        LaunchedEffect(companyIdNullable) {
            companyIdNullable?.let { companyViewModel.fetchInboxByCompany(it) }
        }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = NavRoutes.GridPublicationsScreenS.ROUTE,
                onScreenSelected = { /* navController.navigate(it) */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Cabecera
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
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

            // Grid de publicaciones
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(publications) { publication: PublicationByCompanyDTO ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable {
                                companyViewModel.selectPublication(publication.id)



                                companyIdNullable?.let {
                                    companyViewModel.fetchInboxByCompany(it)

                                }


                                navController.navigate(NavRoutes.IntershipScreen.ROUTE)

                            }
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = publication.file,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = publication.location.name,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                text = stringResource(
                                    if (publication.paid) R.string.estado_pagado else R.string.estado_no_pagado
                                ),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}