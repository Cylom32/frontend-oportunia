package com.example.oportunia.presentation.ui.screens

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import java.io.File
import java.io.FileOutputStream
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.text.font.FontStyle
import coil.compose.AsyncImage
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.Area
import com.example.oportunia.domain.model.Location
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun HomeScreenS(companyViewModel: CompanyViewModel, usersViewModel: UsersViewModel, navController: NavHostController) {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        var selectedCompanyId by remember { mutableStateOf<Int?>(null) }

        var showMainPopup by remember { mutableStateOf(false) }
        var showAreaPopup by remember { mutableStateOf(false) }
        var showUbicacionPopup by remember { mutableStateOf(false) }
        var showModalidadPopup by remember { mutableStateOf(false) }
        var showRemuneradoPopup by remember { mutableStateOf(false) }

        // Estados para filtros
        var selectedAreaIds by remember { mutableStateOf(listOf<Int>()) }
        var selectedLocationIds by remember { mutableStateOf(listOf<Int>()) }
        var selectedRemunerado by remember { mutableStateOf<Boolean?>(null) }

        LaunchedEffect(Unit) {
            companyViewModel.fetchPublications()
        }

        val publications by companyViewModel.publications.collectAsState()



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
                    SearchBar(onMenuClick = {
                        showMainPopup = true
                        usersViewModel.fetchAreas()
                        usersViewModel.fetchLocations()    // <– aquí
                    })

                }



                val publications by companyViewModel.publications.collectAsState()

                // 3) Imagenes en scroll con pull-to-refresh según filtros
                ImageScroll(
                    publications = publications,
                    onRefresh = {
                        // pull-to-refresh: vuelve a llamar con los filtros
                        Log.d("HomeScreenS", "Refresh → filtros: " +
                                "area=${selectedAreaIds.firstOrNull()}, " +
                                "loc=${selectedLocationIds.firstOrNull()}, " +
                                "paid=$selectedRemunerado")
                        companyViewModel.fetchPublications(
                            areaId     = selectedAreaIds.firstOrNull(),
                            locationId = selectedLocationIds.firstOrNull(),
                            paid       = selectedRemunerado
                        )
                    },
                    onPageChange = { companyId ->
                        // cada vez que cambias página
                        Log.d("HomeScreenS", "Página → companyId=$companyId")
                        selectedCompanyId = companyId
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 80.dp, top = 3.dp),
                    companyViewModel,
                    navController
                )



                if (showMainPopup) {
                    FilterPopup(
                        selectedAreaIds = selectedAreaIds,
                        selectedLocationIds = selectedLocationIds,
                        selectedRemunerado = selectedRemunerado,
                        onDismiss = { showMainPopup = false },
                        onConfirm = { areaId, locationId, paid ->
                            showMainPopup = false
                            Log.d("HomeScreenS", "Confirm → filtros: area=$areaId, loc=$locationId, paid=$paid")
                            companyViewModel.fetchPublications(
                                areaId = areaId,
                                locationId = locationId,
                                paid = paid
                            )
                        },
                        onAreaClick = {
                            showMainPopup = false
                            showAreaPopup = true
                        },
                        onUbicacionClick = {
                            showMainPopup = false
                            showUbicacionPopup = true
                        },
                        onModalidadClick = {
                            showMainPopup = false
                            showModalidadPopup = true
                        },
                        onRemuneradoClick = {
                            showMainPopup = false
                            showRemuneradoPopup = true
                        }
                    )
                }


                if (showUbicacionPopup) {

                    val locations by usersViewModel.locations.collectAsState()

                    UbicacionPopup(
                        availableLocations   = locations,
                        selectedLocationIds  = selectedLocationIds,
                        onSelectionChange    = { selectedLocationIds = it },
                        onDismiss            = {
                            showUbicacionPopup = false
                            showMainPopup      = true
                        }
                    )
                }


                if (showAreaPopup) {
                    val areas by usersViewModel.areas.collectAsState()
                    AreaPopup(
                        availableAreas     = areas,
                        selectedAreaIds    = selectedAreaIds,
                        onSelectionChange  = { selectedAreaIds = it },
                        onDismiss          = {
                            showAreaPopup = false
                            showMainPopup = true
                        }
                    )
                }



                if (showRemuneradoPopup) {
                    RemuneradoPopup(
                        selected = selectedRemunerado,
                        onSelectionChange = { selectedRemunerado = it },
                        onDismiss = {
                            showRemuneradoPopup = false
                            showMainPopup = true
                        }
                    )
                }


            }
        }
    }
}


@Composable
fun SearchBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .background(Color(0xFFE9EEF1), shape = RoundedCornerShape(50))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menú",
            tint = Color.DarkGray,
            modifier = Modifier.clickable { onMenuClick() }
        )

        Text(
            text =  stringResource(R.string.etiqueta_buscar_home),
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            color = Color.DarkGray
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Buscar",
            tint = Color.DarkGray
        )
    }
}


@Composable
fun FilterPopup(
    selectedAreaIds: List<Int>,
    selectedLocationIds: List<Int>,
    selectedRemunerado: Boolean?,
    onDismiss: () -> Unit,
    onConfirm: (areaId: Int?, locationId: Int?, paid: Boolean?) -> Unit,
    onAreaClick: () -> Unit,
    onUbicacionClick: () -> Unit,
    onModalidadClick: () -> Unit,
    onRemuneradoClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.etiqueta_filtro_busqueda)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text( stringResource(R.string.etiqueta_area), modifier = Modifier.clickable { onAreaClick() })
                Text( stringResource(R.string.etiqueta_ubicacion), modifier = Modifier.clickable { onUbicacionClick() })
                Text( stringResource(R.string.etiqueta_remunerado), modifier = Modifier.clickable { onRemuneradoClick() })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    selectedAreaIds.firstOrNull(),
                    selectedLocationIds.firstOrNull(),
                    selectedRemunerado
                )
                onDismiss()
            }) {
                Text( stringResource(R.string.boton_cerrar_dialogo))
            }
        }
    )
}



@Composable
fun AreaPopup(
    availableAreas: List<Area>,
    selectedAreaIds: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    onDismiss: () -> Unit
) {
    val current = remember { mutableStateListOf<Int>().apply { addAll(selectedAreaIds) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.etiqueta_seleccionar_area))},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                if (availableAreas.isEmpty()) {
                    Text(stringResource(R.string.etiqueta_cargando_area))
                } else {
                    availableAreas.forEach { area ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (current.contains(area.id)) current.remove(area.id)
                                    else current.add(area.id)
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = current.contains(area.id),
                                onCheckedChange = { checked ->
                                    if (checked) current.add(area.id)
                                    else current.remove(area.id)
                                }
                            )
                            Text(text = area.name)
                        }
                    }
                    if (current.isEmpty()) {
                        Text(stringResource(R.string.etiqueta_ninguna_area), fontStyle = FontStyle.Italic)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(current.toList())
                onDismiss()
            }) {
                Text( stringResource(R.string.ApplyButtonText))
            }
        }
    )
}


@Composable
fun UbicacionPopup(
    availableLocations: List<Location>,
    selectedLocationIds: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    onDismiss: () -> Unit
) {
    val current = remember { mutableStateListOf<Int>().apply { addAll(selectedLocationIds) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.etiqueta_seleccionar_ubicacion)) },
        text = {
            Column {
                if (availableLocations.isEmpty()) {
                    Text(text=stringResource(R.string.etiqueta_cargando_ubicaciones))
                } else {
                    availableLocations.forEach { loc ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (current.contains(loc.id)) current.remove(loc.id)
                                    else current.add(loc.id)
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = current.contains(loc.id),
                                onCheckedChange = { checked ->
                                    if (checked) current.add(loc.id)
                                    else current.remove(loc.id)
                                }
                            )
                            Text(loc.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(current.toList())
                onDismiss()
            }) {
                Text(stringResource(R.string.ApplyButtonText))
            }
        }
    )
}

@Composable
fun RemuneradoPopup(
    selected: Boolean?,
    onSelectionChange: (Boolean?) -> Unit,
    onDismiss: () -> Unit
) {
    val opciones = listOf(
        stringResource(R.string.respuesta_si) to true,
        stringResource(R.string.respuesta_no) to false
    )

    var selectedOption by remember { mutableStateOf(selected) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text= stringResource(R.string.etiqueta_remunerado_q))},
        text = {
            Column {
                opciones.forEach { (label, value) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = if (selectedOption == value) null else value
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == value,
                            onClick = {
                                selectedOption = if (selectedOption == value) null else value
                            }
                        )
                        Text(label)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(selectedOption)
                onDismiss()
            }) {
                Text(stringResource(R.string.ApplyButtonText))
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageScroll(
    publications: List<PublicationFilterDTO>,
    onRefresh: () -> Unit,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    companyViewModel: CompanyViewModel,
    navController: NavHostController
) {
    val images    = publications.map { it.file }
    val logos     = publications.map { it.company.user.img } // URLs reales
    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(pagerState.currentPage) {
        publications.getOrNull(pagerState.currentPage)
            ?.company
            ?.idCompany
            ?.let(onPageChange)
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                onRefresh()
                delay(1000)
                isRefreshing = false
            }
        }
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        if (images.isEmpty()) {
            Text(
                stringResource(R.string.no_hay_publicaciones),
                Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        } else {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = images[page],
                        contentDescription = "Publicación #${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = logos[page],
                        contentDescription = "Logo empresa",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(72.dp)
                            .clip(CircleShape)
                            .clickable {
                                val companyId = publications[page].company.idCompany
                                companyViewModel.setSelectedCompanyId(companyId)
                                navController.navigate(NavRoutes.CompanyInfoScreenS.ROUTE)

                                val logoUrl   = publications[page].company.user.img

                                companyViewModel.setCompanyLogo(logoUrl)

                                companyViewModel.fetchCompanyWithNetworks(companyId)

                                Log.d("ImageScroll", "Logo clicked, companyId=$companyId")
                            }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

