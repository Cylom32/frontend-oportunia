// src/main/java/com/example/oportunia/presentation/ui/screens/EditInformationCompanyScreen.kt
package com.example.oportunia.presentation.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.domain.model.SocialNetwork
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.cloudinary.CloudinaryService
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun EditInformationCompanyScreen(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    companyViewModel: CompanyViewModel
) {
    var companyName by remember { mutableStateOf(TextFieldValue("")) }
    var social1 by remember { mutableStateOf(TextFieldValue("")) }
    var social2 by remember { mutableStateOf(TextFieldValue("")) }
    var social3 by remember { mutableStateOf(TextFieldValue("")) }
    var logoLinks by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionText by remember { mutableStateOf(TextFieldValue("")) }
    var showEmptyAlert by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    // Variables para carga de imagen
    var isUploadingImage by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }
    var publicationLink by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            isUploadingImage = true
            uploadError = null

            coroutineScope.launch {
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val file = File(context.cacheDir, "company_logo_${System.currentTimeMillis()}.jpg").apply {
                        outputStream().use { output ->
                            inputStream?.copyTo(output)
                        }
                    }

                    val cloudinaryService = CloudinaryService("dfffvf0m6", "mi_preset")
                    val url = cloudinaryService.uploadImage(file)

                    if (url != null) {
                        publicationLink = url
                        // Asigna inmediatamente el nuevo link al campo logoLinks:
                        logoLinks = TextFieldValue(publicationLink)
                        uploadError = null
                    } else {
                        uploadError = "Error al subir la imagen"
                    }
                } catch (e: Exception) {
                    uploadError = "Error: ${e.message}"
                } finally {
                    isUploadingImage = false
                }
            }
        }
    }

    val socialList by companyViewModel.companySocialNetworks.collectAsState()
    val imgValue by companyViewModel.imgShow.collectAsState()
    val nameValue by companyViewModel.companyNameC.collectAsState()
    val descValue by companyViewModel.companyDescriptionC.collectAsState()

    LaunchedEffect(Unit) {
        companyViewModel.fetchCompanySocialNetworks()
    }
    LaunchedEffect(socialList) {
        social1 = TextFieldValue(socialList.getOrNull(0)?.link.orEmpty())
        social2 = TextFieldValue(socialList.getOrNull(1)?.link.orEmpty())
        social3 = TextFieldValue(socialList.getOrNull(2)?.link.orEmpty())
    }
    LaunchedEffect(nameValue, imgValue, descValue) {
        companyName = TextFieldValue(nameValue.orEmpty())
        // Inicializa logoLinks con el valor actual de imgValue
        logoLinks = TextFieldValue(imgValue.orEmpty())
        descriptionText = TextFieldValue(descValue.orEmpty())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = gradientColorsBlue,
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(lilGray),
            color = Color.Transparent
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Encabezado
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
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
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 64.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.screenTitleInfo),
                        fontSize = 32.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 16.dp)
                    )

                    // Nombre de la compañía
                    Text(
                        text = stringResource(R.string.etiqueta_nombre_compania),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = companyName,
                            onValueChange = { companyName = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Redes sociales 1, 2 y 3
                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social1,
                            onValueChange = { social1 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social2,
                            onValueChange = { social2 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    Text(
                        text = stringResource(R.string.etiqueta_red_social),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        BasicTextField(
                            value = social3,
                            onValueChange = { social3 = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    // Imagen de la compañía
                    Text(
                        text = stringResource(R.string.imagen_compañia),
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    )

                    Button(
                        onClick = {
                            if (!isUploadingImage) {
                                launcher.launch("image/*")
                            }
                        },
                        enabled = !isUploadingImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isUploadingImage) Color.Gray else royalBlue
                        )
                    ) {
                        when {
                            isUploadingImage -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(R.string.subiendo),
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                }
                            }
                            publicationLink.isNotEmpty() -> {
                                Text(
                                    text = stringResource(R.string.imagen_seleccionada),
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            else -> {
                                Text(
                                    text = stringResource(R.string.seleccionar_imagen),
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Mostrar errores de carga
                    uploadError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button para agregar descripción
                    Button(
                        onClick = { /* abre diálogo de descripción */ },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp), clip = false),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = stringResource(R.string.Agregar_Descripcion),
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botones Cancelar y Confirmar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .shadow(
                                    elevation = 6.dp,
                                    shape = RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .background(Color.LightGray)
                                .clickable {
                                    navController.popBackStack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.Cancelar),
                                fontSize = 18.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            royalBlue,
                                            deepSkyBlue,
                                            midnightBlue
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(1000f, 1000f)
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .clickable {
                                    val nameOk = companyName.text.isNotBlank()
                                    val logoOk = logoLinks.text.isNotBlank()
                                    val descOk = descriptionText.text.isNotBlank()
                                    val atLeastOneSocial = social1.text.isNotBlank() ||
                                            social2.text.isNotBlank() ||
                                            social3.text.isNotBlank()

                                    if (nameOk && logoOk && descOk && atLeastOneSocial) {
                                        showConfirmDialog = true
                                    } else {
                                        showEmptyAlert = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.bTextConfirmar),
                                fontSize = 18.sp,
                                color = walterWhite,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Diálogo de confirmación
            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text(text = stringResource(R.string.Confirmacion_Guardar)) },
                    text = { Text(text = stringResource(R.string.mensaje_guardar)) },
                    confirmButton = {
                        TextButton(onClick = {
                            // 1) Construir la lista actualizada de redes sociales
                            val updatedSocials = mutableListOf<SocialNetwork>()
                            socialList.getOrNull(0)?.let { sn0 ->
                                updatedSocials.add(
                                    SocialNetwork(
                                        idSocialNetwork = sn0.idSocialNetwork,
                                        link = social1.text
                                    )
                                )
                            }
                            socialList.getOrNull(1)?.let { sn1 ->
                                updatedSocials.add(
                                    SocialNetwork(
                                        idSocialNetwork = sn1.idSocialNetwork,
                                        link = social2.text
                                    )
                                )
                            }
                            socialList.getOrNull(2)?.let { sn2 ->
                                updatedSocials.add(
                                    SocialNetwork(
                                        idSocialNetwork = sn2.idSocialNetwork,
                                        link = social3.text
                                    )
                                )
                            }

                            // 2) Imprimir valores antes de llamar al ViewModel
                            Log.d("EditCompany", "→ companyNameText = ${companyName.text}")
                            Log.d("EditCompany", "→ descriptionText = ${descriptionText.text}")
                            Log.d("EditCompany", "→ logoLinkText = ${logoLinks.text}")
                            updatedSocials.forEach { sn ->
                                Log.d("EditCompany", "→ social id=${sn.idSocialNetwork}, link=${sn.link}")
                            }

                            // 3) Llamar al método del ViewModel
                            companyViewModel.confirmEditCompany(
                                companyName.text,
                                descriptionText.text,
                                logoLinks.text,
                                updatedSocials
                            )

                            // 4) Navegar luego de guardar
                            navController.navigate(NavRoutes.SettingScreenCompany.ROUTE)

                            showConfirmDialog = false
                        }) {
                            Text(text = stringResource(R.string.etiqueta_ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmDialog = false }) {
                            Text(text = stringResource(R.string.Cancelar))
                        }
                    }
                )
            }


            // Diálogo de alerta si faltan campos
            if (showEmptyAlert) {
                AlertDialog(
                    onDismissRequest = { showEmptyAlert = false },
                    title = { Text(text = stringResource(R.string.Alerta)) },
                    text = {
                        Text(
                            text = stringResource(R.string.alerta_alerta)
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showEmptyAlert = false }) {
                            Text(text = stringResource(R.string.etiqueta_ok))
                        }
                    }
                )
            }
        }
    }
}
