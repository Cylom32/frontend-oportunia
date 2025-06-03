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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.oportunia.R
import com.example.oportunia.domain.model.Area
import com.example.oportunia.domain.model.Location
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



    LaunchedEffect(Unit) {
        usersViewModel.fetchAreas()
        usersViewModel.fetchLocations()
    }



    var isPaid by remember { mutableStateOf(true) }
    var publicationLink by remember { mutableStateOf("") }


    val areaOptions = usersViewModel.areas.collectAsState()
    val locationOptions = usersViewModel.locations.collectAsState()

    var selectedArea by remember { mutableStateOf<Area?>(null) }
    var selectedLocation by remember { mutableStateOf<Location?>(null) }


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
                Text(stringResource(R.string.agregar_pasantia))
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

                                companyViewModel.fetchPublicationById(publication.id)
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
                                    text = if (publication.paid) stringResource(R.string.estado_pagado) else stringResource(R.string.estado_no_pagado),
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
                    title = { Text(stringResource(R.string.nueva_pasantia))},
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            // Área
                            // Área
                            var areaExpanded by remember { mutableStateOf(false) }
                            Text(stringResource(R.string.etiqueta_area))
                            Box {
                                OutlinedTextField(
                                    value = selectedArea?.name ?: "Seleccione área",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(stringResource(R.string.etiqueta_seleccionar_area)) },
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
                                    areaOptions.value.forEach { area ->
                                        DropdownMenuItem(
                                            text = { Text(area.name) },
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
                            // Ubicación
                            var locationExpanded by remember { mutableStateOf(false) }
                            Text(stringResource(R.string.etiqueta_ubicacion))
                            Box {
                                OutlinedTextField(
                                    value = selectedLocation?.name ?: "Seleccione ubicación",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(stringResource(R.string.etiqueta_seleccionar_ubicacion)) },
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
                                    locationOptions.value.forEach { location ->
                                        DropdownMenuItem(
                                            text = { Text(location.name) },
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
                            Text(stringResource(R.string.es_pagada))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = isPaid, onClick = { isPaid = true })
                                Text(stringResource(R.string.respuesta_si))
                                Spacer(modifier = Modifier.width(16.dp))
                                RadioButton(selected = !isPaid, onClick = { isPaid = false })
                                Text(stringResource(R.string.respuesta_no))
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Link
                            OutlinedTextField(
                                value = publicationLink,
                                onValueChange = { publicationLink = it },
                                label = { Text(stringResource(R.string.link_publicacion)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // Capturar en locales para smart-cast
                                val areaSeleccionada = selectedArea
                                val ubicacionSeleccionada = selectedLocation

                                if (
                                    areaSeleccionada != null &&
                                    ubicacionSeleccionada != null &&
                                    publicationLink.isNotBlank()
                                ) {
                                    // Crear pasantía
                                    val currentToken = tokenState
                                    val currentCompanyId = companyIdState

                                    val job = companyViewModel.createPublication(
                                        file = publicationLink,
                                        paid = isPaid,
                                        idLocation = ubicacionSeleccionada.id,
                                        idArea = areaSeleccionada.id
                                    )

                                    job.invokeOnCompletion {
                                        if (!currentToken.isNullOrEmpty() && currentCompanyId != null) {
                                            companyViewModel.fetchPublicationsByCompany(currentToken, currentCompanyId)
                                        }
                                    }

                                    // Resetear campos del popup (usar las vars del estado, NO las val locales)
                                    selectedArea = null
                                    selectedLocation = null
                                    publicationLink = ""
                                    isPaid = true

                                    showDialog.value = false
                                }
                            }
                        ) {
                            Text(stringResource(R.string.subir))
                        }
                    }

                    ,
                    dismissButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text(stringResource(R.string.Cancelar))
                        }
                    }
                )
            }
        }
    }
}
