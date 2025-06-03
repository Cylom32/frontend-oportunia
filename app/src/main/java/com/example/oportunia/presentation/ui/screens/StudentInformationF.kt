package com.example.oportunia.presentation.ui.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.domain.model.University
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.components.texAndLable
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults


/** Guardad aquí el ID seleccionado */
var idSelectedU: Int = 0




@Composable
fun RegisterOptionScreenF(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {

    var nombre by remember { mutableStateOf("") }
    var primerApellido by remember { mutableStateOf("") }
    var segundoApellido by remember { mutableStateOf("") }

    var selectedUniversityName by remember { mutableStateOf("") }
    var selectedUniversityId   by remember { mutableStateOf(0) }





    var selectedUniversity by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }

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
            // -- Encabezado --
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .gradientBackgroundBlue(gradientColors = gradientColorsBlue,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 64.sp,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(32.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {



                texAndLable(
                    titulo = stringResource(R.string.nameTitle),
                    placeholder = stringResource(R.string.nameTitle),
                    valor = nombre,
                    alCambiarValor = { nombre = it }
                )
                Spacer(Modifier.height(8.dp))
                texAndLable(
                    titulo = stringResource(R.string.FirstSurnameTitle),
                    placeholder = stringResource(R.string.FirstSurnameTitle),
                    valor = primerApellido,
                    alCambiarValor = { primerApellido = it }
                )
                Spacer(Modifier.height(8.dp))
                texAndLable(
                    titulo = stringResource(R.string.SecondSurnameTitle),
                    placeholder = stringResource(R.string.SecondSurnameTitle),
                    valor = segundoApellido,
                    alCambiarValor = { segundoApellido = it }
                )
            }

            Spacer(Modifier.height(24.dp))




            val uni by usersViewModel.universities.collectAsState()

            var selectedUniversityName by remember { mutableStateOf("") }
            var selectedUniversityId   by remember { mutableStateOf(0) }

            Column {
                UniversityDropdown(
                    selectedUniversity   = selectedUniversityName,
                    selectedUniversityId = selectedUniversityId,
                    onUniversitySelected = { name, id ->
                        selectedUniversityName = name
                        selectedUniversityId   = id
                    },
                    options              = uni
                )

                Spacer(Modifier.height(16.dp))


}

            Spacer(Modifier.height(120.dp))


            Box(
                modifier = Modifier
                    .width(350.dp)
                    .shadow(10.dp, RoundedCornerShape(24.dp))
                    .gradientBackgroundBlue(
                        gradientColors = gradientColorsBlue,
                        shape = RoundedCornerShape(24.dp)
                    ).clickable {
                        if (
                            nombre.isNotBlank() &&
                            primerApellido.isNotBlank() &&
                            segundoApellido.isNotBlank() &&
                            selectedUniversityName.isNotBlank() &&
                            selectedUniversityId != 0
                        ) {

                            usersViewModel.saveAndLogRegistration(
                                nombre,
                                primerApellido,
                                segundoApellido,
                                selectedUniversityName,
                                selectedUniversityId
                            )

                            navController.navigate(NavRoutes.RegisterInformationPAndE.ROUTE)

                        } else {
                            showAlert = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.texBoton),
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text(stringResource(R.string.acceptText))
                }
            },
            title = { Text(stringResource(R.string.studentInfoAlertTittle)) },
            text  = { Text(stringResource(R.string.studentInfoAlertText)) }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityDropdown(
    selectedUniversity: String,
    selectedUniversityId: Int,
    onUniversitySelected: (String, Int) -> Unit,
    options: List<University>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded         = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier         = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)
    ) {

        OutlinedTextField(
            value          = selectedUniversity,
            onValueChange  = { /* readOnly */ },
            readOnly       = true,
            textStyle      = LocalTextStyle.current.copy(color = Color.Black),
            label          = { Text(stringResource(R.string.UniversityTitle), color = Color.Black) },
            trailingIcon   = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier       = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )


        if (expanded) {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    surface   = Color.DarkGray,
                    onSurface = Color.White
                )
            ) {
                ExposedDropdownMenu(
                    expanded        = true,
                    onDismissRequest= { expanded = false },
                    modifier        = Modifier.fillMaxWidth(0.8f)

                ) {
                    options.forEach { uni ->
                        DropdownMenuItem(
                            text    = { Text(uni.universityName) },
                            onClick = {
                                expanded = false
                                onUniversitySelected(uni.universityName,
                                    uni.idUniversity ?: 0)
                            }
                        )
                    }
                }
            }
        }
    }
}
