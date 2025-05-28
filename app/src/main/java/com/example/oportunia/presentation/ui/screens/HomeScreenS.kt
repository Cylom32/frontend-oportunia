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
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun HomeScreenS() {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        var showMainPopup by remember { mutableStateOf(false) }

        var showAreaPopup by remember { mutableStateOf(false) }
        var selectedAreas by remember { mutableStateOf(listOf<String>()) }
        var showUbicacionPopup by remember { mutableStateOf(false) }

        var selectedProvinces by remember { mutableStateOf(listOf<String>()) }
        var showModalidadPopup by remember { mutableStateOf(false) }
        var showRemuneradoPopup by remember { mutableStateOf(false) }

        var selectedModalidad by remember { mutableStateOf<String?>(null) }
        var selectedRemunerado by remember { mutableStateOf<String?>(null) }



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
                    SearchBar(onMenuClick = { showMainPopup = true })
                }



                ImageScroll(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // ocupa todo el espacio restante
                        .padding(bottom = 80.dp, top = 3.dp)
                )


                if (showMainPopup) {
                    FilterPopup(
                        onDismiss = { showMainPopup = false },
                        onConfirm = { showMainPopup = false },
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
                    UbicacionPopup(
                        selectedProvinces = selectedProvinces,
                        onSelectionChange = { selectedProvinces = it },
                        onDismiss = {
                            showUbicacionPopup = false
                            showMainPopup = true
                        }
                    )
                }


                if (showAreaPopup) {
                    AreaPopup(
                        selectedAreas = selectedAreas,
                        onSelectionChange = { selectedAreas = it },
                        onDismiss = {
                            showAreaPopup = false
                            showMainPopup = true
                        }
                    )
                }

                if (showModalidadPopup) {
                    ModalidadPopup(
                        selected = selectedModalidad,
                        onSelectionChange = { selectedModalidad = it },
                        onDismiss = {
                            showModalidadPopup = false
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
            text = "Buscar",
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
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onAreaClick: () -> Unit,
    onUbicacionClick: () -> Unit,
    onModalidadClick: () -> Unit,
    onRemuneradoClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filtros de búsqueda") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Área", modifier = Modifier.clickable { onAreaClick() })
                Text("Ubicación", modifier = Modifier.clickable { onUbicacionClick() })
                Text("Modalidad", modifier = Modifier.clickable { onModalidadClick() })
                Text("Remunerado", modifier = Modifier.clickable { onRemuneradoClick() })
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}


@Composable
fun AreaPopup(
    selectedAreas: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf("Salud", "Tecnología", "Software")
    val currentSelection = remember { mutableStateListOf<String>().apply { addAll(selectedAreas) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar Área") },
        text = {
            Column {
                options.forEach { area ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (currentSelection.contains(area)) {
                                    currentSelection.remove(area)
                                } else {
                                    currentSelection.add(area)
                                }
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = currentSelection.contains(area),
                            onCheckedChange = {
                                if (it) currentSelection.add(area) else currentSelection.remove(area)
                            }
                        )
                        Text(area)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(currentSelection)
                onDismiss()
            }) {
                Text("Aplicar")
            }
        }
    )
}


@Composable
fun UbicacionPopup(
    selectedProvinces: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val provincias = listOf(
        "San José", "Alajuela", "Cartago",
        "Heredia", "Guanacaste", "Puntarenas", "Limón"
    )
    val currentSelection = remember { mutableStateListOf<String>().apply { addAll(selectedProvinces) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar Ubicación") },
        text = {
            Column {
                provincias.forEach { provincia ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (currentSelection.contains(provincia)) {
                                    currentSelection.remove(provincia)
                                } else {
                                    currentSelection.add(provincia)
                                }
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = currentSelection.contains(provincia),
                            onCheckedChange = {
                                if (it) currentSelection.add(provincia) else currentSelection.remove(provincia)
                            }
                        )
                        Text(provincia)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(currentSelection)
                onDismiss()
            }) {
                Text("Aplicar")
            }
        }
    )
}

@Composable
fun ModalidadPopup(
    selected: String?,
    onSelectionChange: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    val opciones = listOf("Mixta", "Presencial", "Remota")
    var selectedOption by remember { mutableStateOf(selected) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar Modalidad") },
        text = {
            Column {
                opciones.forEach { opcion ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = opcion }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == opcion,
                            onClick = { selectedOption = opcion }
                        )
                        Text(opcion)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(selectedOption)
                onDismiss()
            }) {
                Text("Aplicar")
            }
        }
    )
}


@Composable
fun RemuneradoPopup(
    selected: String?,
    onSelectionChange: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    val opciones = listOf("Sí", "No")
    var selectedOption by remember { mutableStateOf(selected) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Remunerado?") },
        text = {
            Column {
                opciones.forEach { opcion ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = opcion }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == opcion,
                            onClick = { selectedOption = opcion }
                        )
                        Text(opcion)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSelectionChange(selectedOption)
                onDismiss()
            }) {
                Text("Aplicar")
            }
        }
    )
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageScroll(modifier: Modifier = Modifier) {
    val imageUrl = "https://images.unsplash.com/photo-1564754943164-e83c08469116?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8dmVydGljYWx8ZW58MHx8MHx8fDA%3D"
    val images = List(10) { imageUrl }
    val logos  = List(10) { imageUrl }
    val pagerState = rememberPagerState(pageCount = { images.size })

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, {
        isRefreshing = true
        coroutineScope.launch {
            delay(1000)
            isRefreshing = false
        }
    })

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(Modifier.fillMaxSize()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Imagen full screen",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds // → estira para cubrir todo, sin bordes
                )



                AsyncImage(
                    model = logos[page],
                    contentDescription = "Logo $page",
                    modifier = Modifier
                        .size(72.dp)
                        .padding(12.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .align(Alignment.TopEnd)
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}






