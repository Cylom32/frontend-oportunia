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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import coil.compose.AsyncImage
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel


//@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)






///////////// PENDIENTE ENVIAR SOLICITUDDDDDDDDDDD





@Composable
fun IntershipScreen(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel
) {

    val selectedId by companyViewModel.selectedPublicationId.collectAsState()

    // 2. Obtenemos el token como String (no String?)
    //    Si usersViewModel.token fuese StateFlow<String?>, inicializamos con ""
    val token by usersViewModel.token.collectAsState(initial = "")

    // 3. Obtenemos el studentIdd como Int? (inicialmente null)
    val studentId by studentViewModel.studentIdd.collectAsState(initial = null)

    // 4. Cada vez que cambie selectedId, traemos el detalle de publicación
    LaunchedEffect(selectedId) {
        selectedId?.let { companyViewModel.fetchPublicationById(it) }
    }

    val publication by companyViewModel.publicationDetail.collectAsState()
    LaunchedEffect(Unit) {

    }

    val publicationDetail by companyViewModel.publicationDetail.collectAsState()

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


                Button(
                    onClick = {
                        // -------- CORRECCIÓN DE NULLABILITY --------
                        // Usamos el “safe let” para desmaterializar token y studentId
                        token?.let { t ->
                            studentId?.let { id ->
                                // Aquí, tanto `t` como `id` ya son no nulos: String y Int
                                studentViewModel.fetchCvList(t, id)
                          //      Log.d("IntershipScreen", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                                navController.navigate(NavRoutes.RequestScreen.ROUTE)
                            }
                        }
                        // Si `token` o `studentId` fueran null, el bloque `let` NUNCA se ejecuta.
                        // Opcionalmente podrías hacer:
                        //   if (token.isNullOrBlank() || studentId == null) {
                               Log.e("IntershipScreen", "Falta token o studentId")
                        //   }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .height(60.dp)
                        .width(180.dp),
                    shape = RoundedCornerShape(24.dp)
                )  {
                    Text(text = "Aplicar", fontSize = 18.sp, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
                }

            }
        }
    }
}
