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
import androidx.compose.material.icons.filled.ArrowDropDown

import coil.compose.AsyncImage
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel


@Composable
fun GridPublicationsCompany(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel
) {
    val tokenState by companyViewModel.tokenC.collectAsState()
    val companyIdState by companyViewModel.companyIdC.collectAsState()
    val publications by companyViewModel.companyPublications.collectAsState(initial = emptyList())
    val companyName by companyViewModel.companyNameC.collectAsState(initial = null)
    val nameToShow = companyName.orEmpty()

    val showDialog = remember { mutableStateOf(false) }

    // Estados del formulario emergente
    val areaOptions = listOf("Ingeniería", "Administración", "Diseño")
    val locationOptions = listOf("San José", "Heredia", "Cartago")
    var selectedArea by remember { mutableStateOf(areaOptions.first()) }
    var selectedLocation by remember { mutableStateOf(locationOptions.first()) }
    var isPaid by remember { mutableStateOf(true) }
    var publicationLink by remember { mutableStateOf("") }

    LaunchedEffect(tokenState, companyIdState) {
        val token = tokenState
        val compId = companyIdState
        if (!token.isNullOrEmpty() && compId != null) {
            companyViewModel.fetchPublicationsByCompany(token, compId)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(lilGray)
        ) {
            // Cabecera
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .gradientBackgroundBlue(
                        gradientColorsBlue,
                        RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.intellogo),
//                        contentDescription = "Logo placeholder",
//                        modifier = Modifier
//                            .size(40.dp)
//                            .clip(CircleShape),
//                        contentScale = ContentScale.Crop,
//                        colorFilter = ColorFilter.tint(walterWhite)
//                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = nameToShow,
                        color = walterWhite,
                        fontSize = 29.sp
                    )
                }
            }

            // Botón Agregar
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Agregar pasantía")
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
                items(publications) { publication ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable {
                                /// aqui hay que setearlo buscando de verdad no lo de abajo
                                companyViewModel.fetchPublicationById(publication.id)
                                companyViewModel.selectPublication(publication.id)
                                navController.navigate(NavRoutes.PublicationDetailScreen.ROUTE)
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = publication.file,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = publication.location.name,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Text(
                                    text = if (publication.paid) "Pagada" else "No pagada",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            IconButton(
                                onClick = {
                                    companyViewModel.deletePublicationById(publication.id)
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                            }

                        }
                    }
                }
            }

            // Diálogo emergente
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Nueva pasantía") },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            // Área
                            var areaExpanded by remember { mutableStateOf(false) }
                            Text("Área")
                            Box {
                                OutlinedTextField(
                                    value = selectedArea,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Seleccione área") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Expand",
                                            modifier = Modifier.clickable { areaExpanded = true }
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { areaExpanded = true }
                                )
                                DropdownMenu(
                                    expanded = areaExpanded,
                                    onDismissRequest = { areaExpanded = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    areaOptions.forEach { area ->
                                        DropdownMenuItem(
                                            text = { Text(area) },
                                            onClick = {
                                                selectedArea = area
                                                areaExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Ubicación
                            var locationExpanded by remember { mutableStateOf(false) }
                            Text("Ubicación")
                            Box {
                                OutlinedTextField(
                                    value = selectedLocation,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Seleccione ubicación") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Expand",
                                            modifier = Modifier.clickable { locationExpanded = true }
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { locationExpanded = true }
                                )
                                DropdownMenu(
                                    expanded = locationExpanded,
                                    onDismissRequest = { locationExpanded = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    locationOptions.forEach { location ->
                                        DropdownMenuItem(
                                            text = { Text(location) },
                                            onClick = {
                                                selectedLocation = location
                                                locationExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // ¿Pagada?
                            Text("¿Es pagada?")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = isPaid, onClick = { isPaid = true })
                                Text("Sí")
                                Spacer(modifier = Modifier.width(16.dp))
                                RadioButton(selected = !isPaid, onClick = { isPaid = false })
                                Text("No")
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Link
                            OutlinedTextField(
                                value = publicationLink,
                                onValueChange = { publicationLink = it },
                                label = { Text("Link de la publicación") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                Log.d("NuevaPasantia", "Área: $selectedArea")
                                Log.d("NuevaPasantia", "Ubicación: $selectedLocation")
                                Log.d("NuevaPasantia", "Pagada: $isPaid")
                                Log.d("NuevaPasantia", "Link: $publicationLink")
                                showDialog.value = false
                            }
                        ) {
                            Text("Subir")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }


        }
    }
}
