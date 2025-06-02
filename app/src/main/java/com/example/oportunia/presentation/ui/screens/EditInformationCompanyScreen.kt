// RegisterInformationCompanyScreen.kt
package com.example.oportunia.presentation.ui.screens

import android.util.Log
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
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel







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
    var logoLink by remember { mutableStateOf(TextFieldValue("")) }
    var showDescriptionDialog by remember { mutableStateOf(false) }
    var descriptionText by remember { mutableStateOf(TextFieldValue("")) }
    var showEmptyAlert by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

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
        logoLink = TextFieldValue(imgValue.orEmpty())
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

                    Text(
                        text = "Nombre de la Compañía",
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

                    Text(
                        text = "Red Social 1",
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
                        text = "Red Social 2",
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
                        text = "Red Social 3",
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

                    Text(
                        text = "Link del Logo",
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
                            value = logoLink,
                            onValueChange = { logoLink = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showDescriptionDialog = true },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp), clip = false),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "AGREGAR DESCRIPCIÓN",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

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
                                text = "CANCELAR",
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
                                    val logoOk = logoLink.text.isNotBlank()
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

            if (showDescriptionDialog) {
                AlertDialog(
                    onDismissRequest = { showDescriptionDialog = false },
                    title = { Text(text = "Descripción") },
                    text = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .padding(horizontal = 8.dp, vertical = 12.dp)
                        ) {
                            BasicTextField(
                                value = descriptionText,
                                onValueChange = { descriptionText = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                singleLine = false
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showDescriptionDialog = false }) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDescriptionDialog = false }) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }

            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text(text = "¿Está seguro?") },
                    text = { Text(text = "Se guardarán los datos ingresados.") },
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
                            Log.d("EditCompany", "→ logoLinkText = ${logoLink.text}")
                            updatedSocials.forEach { sn ->
                                Log.d("EditCompany", "→ social id=${sn.idSocialNetwork}, link=${sn.link}")
                            }

                            // 3) Llamar al método del ViewModel con la lista actualizada
                            companyViewModel.confirmEditCompany(
                                companyName.text,
                                descriptionText.text,
                                logoLink.text,
                                updatedSocials
                            )
                            showConfirmDialog = false
                        }) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmDialog = false }) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }


            if (showEmptyAlert) {
                AlertDialog(
                    onDismissRequest = { showEmptyAlert = false },
                    title = { Text(text = "Alerta") },
                    text = {
                        Text(
                            text = "Debe llenar Nombre, Link de Logo, Descripción y al menos una Red Social"
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showEmptyAlert = false }) {
                            Text(text = "OK")
                        }
                    }
                )
            }
        }
    }
}
