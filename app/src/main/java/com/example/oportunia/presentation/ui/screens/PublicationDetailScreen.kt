package com.example.oportunia.presentation.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import coil.compose.AsyncImage
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel








@Composable
fun PublicationDetailScreen(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel,
    paddingValues: PaddingValues
) {
    val publicationDetail by companyViewModel.publicationDetail.collectAsState()
    val companyName by companyViewModel.companyNameC.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lilGray)
            .padding(paddingValues)
    ) {
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
            Text(
                text = companyName.orEmpty(),
                color = walterWhite,
                fontSize = 29.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AsyncImage(
                model = publicationDetail?.file,
                contentDescription = "Imagen de publicación",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp + paddingValues.calculateBottomPadding())
                    .background(Color(0xAAFFFFFF), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.etiqueta_area)+": ${publicationDetail?.area?.name.orEmpty()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.etiqueta_ubicacion)+": ${publicationDetail?.location?.name.orEmpty()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = if (publicationDetail?.paid == true) stringResource(R.string.tipo_pagada) else stringResource(R.string.tipo_no_pagada),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
