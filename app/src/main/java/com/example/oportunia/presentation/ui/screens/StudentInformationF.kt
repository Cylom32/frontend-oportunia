
package com.example.oportunia.presentation.ui.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.oportunia.presentation.ui.theme.lilRedMain
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.texAndLable
import com.example.oportunia.presentation.ui.theme.blackPanter
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel

var idSelectedU = 0

@Composable
fun RegisterOptionScreenF(studentViewModel: StudentViewModel, navController: NavHostController) {



    var showAlert by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.oportunia.presentation.ui.theme.lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(com.example.oportunia.presentation.ui.theme.lilGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                    .background(
                        color = com.example.oportunia.presentation.ui.theme.lilRedMain,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
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

            // Título
            Text(
                text = stringResource(R.string.screenTitleInfo),
                fontSize = 32.sp,
                color = com.example.oportunia.presentation.ui.theme.blackPanter,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                val nombre by studentViewModel.nombre.collectAsState()
                val apellido1 by studentViewModel.apellido1.collectAsState()
                val apellido2 by studentViewModel.apellido2.collectAsState()

                texAndLable(
                    titulo = stringResource(R.string.nameTitle),
                    placeholder = "",
                    valor = nombre,
                    alCambiarValor = { studentViewModel.setNombre(it) }
                )


                texAndLable(
                    titulo = stringResource(R.string.FirstSurnameTitle),
                    placeholder = "",
                    valor = apellido1,
                    alCambiarValor = { studentViewModel.setApellido1(it) }
                )


                texAndLable(
                    titulo = stringResource(R.string.SecondSurnameTitle),
                    placeholder = "",
                    valor = apellido2,
                    alCambiarValor = { studentViewModel.setApellido2(it) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                var selectedUniversity by remember { mutableStateOf("    ") }


                UniversityDropdown(
                    selectedUniversity = selectedUniversity,
                    onUniversitySelected = { selectedUniversity = it },
                    studentViewModel
                )
            }

            Spacer(modifier = Modifier.height(120.dp))



            Box(
                modifier = Modifier
                    .width(350.dp)
                    .padding(horizontal = 60.dp, vertical = 0.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false
                    )
                    .background(
                        color = com.example.oportunia.presentation.ui.theme.lilRedMain,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {

                        val nombre = studentViewModel.nombre.value.trim()
                        val apellido1 = studentViewModel.apellido1.value.trim()
                        val apellido2 = studentViewModel.apellido2.value.trim()
                        val universidadId = idSelectedU

                        studentViewModel.setIdUniversidad(universidadId)

                        if (nombre.isNotEmpty() && apellido1.isNotEmpty() && apellido2.isNotEmpty() && universidadId != 0) {
                            navController.navigate(NavRoutes.RegisterInformationPAndE.ROUTE)
                        } else {
                            showAlert = true
                        }
                    }
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.texBoton),
                    fontSize = 25.sp,
                    color = com.example.oportunia.presentation.ui.theme.walterWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("Aceptar")
                }
            },
            title = { Text(stringResource(R.string.studentInfoAlertTittle)) },
            text = { Text(stringResource(R.string.studentInfoAlertText)) }
        )
    }

}

@Composable
fun UniversityDropdown(
    selectedUniversity: String,
    onUniversitySelected: (String) -> Unit,
    studentViewModel: StudentViewModel
) {

    LaunchedEffect(Unit) {
        studentViewModel.loadUniversityOptions()
    }

    val options by studentViewModel.universityOptions.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(150.dp)) {
        OutlinedTextField(
            value = selectedUniversity,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.UniversityTitle), color = Color.Black) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { university ->
                DropdownMenuItem(
                    text = { Text(university.name, color = Color.Black) },
                    onClick = {

                        Log.d(
                            "UniversityDropdown",
                            "Seleccionado: ${university.name} (ID: ${university.id})"
                        )
                        // formatearUniversidad(university.id)
                        idSelectedU = university.id
                        onUniversitySelected(university.name)
                        expanded = false
                    }
                )
            }
        }
    }

}


