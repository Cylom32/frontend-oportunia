package com.example.oportunia.presentation.ui.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import coil.compose.AsyncImage
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel








@Composable
fun IntershipScreen(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel
) {

    val selectedId by companyViewModel.selectedPublicationId.collectAsState()

    LaunchedEffect(selectedId) {
        selectedId?.let {
            companyViewModel.fetchPublicationById(it)
            // AQUÍ AGREGAMOS: También obtener el inbox de la compañía de esta publicación
            companyViewModel.publicationDetail.value?.let { publication ->
                val companyId = publication.company.idCompany
                if (companyId != null) {
                    companyViewModel.fetchInboxByCompany(companyId)
                } // Asumiendo que existe este método
            }
        }
    }

    val publicationDetail by companyViewModel.publicationDetail.collectAsState()

    // Obtener token y studentId
    val token by usersViewModel.token.collectAsState(initial = null)
    val studentId by studentViewModel.studentIdd.collectAsState(initial = null)

    // Observar la lista de CVs
    val cvList by studentViewModel.cvlista.collectAsState(initial = emptyList())

    // Cargar lista de CVs cuando token o studentId cambien
    LaunchedEffect(token, studentId) {
        if (!token.isNullOrEmpty() && studentId != null) {
            studentViewModel.fetchCvList(token!!, studentId!!)
        }
    }

    // NUEVO: Cargar inbox cuando tengamos el detalle de la publicación
    LaunchedEffect(publicationDetail) {
        publicationDetail?.let { publication ->
            val companyId = publication.company.idCompany
            companyId?.let { companyViewModel.fetchInboxByCompany(it) }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = NavRoutes.GridPublicationsScreenS.ROUTE,
                onScreenSelected = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                val companyName by companyViewModel.companyName.collectAsState()
                Text(
                    text = stringResource(R.string.pasantia),
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

                Button(
                    onClick = {
                        if (cvList.isEmpty()) {
                            // No hay CV, navegar a pantalla de edición para crear uno
                            navController.navigate(NavRoutes.EditUCVScreen.ROUTE)
                        } else {
                            // Hay CVs, navegar a pantalla de solicitud
                            navController.navigate(NavRoutes.RequestScreen.ROUTE)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .height(60.dp)
                        .width(180.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ApplyButtonText),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}