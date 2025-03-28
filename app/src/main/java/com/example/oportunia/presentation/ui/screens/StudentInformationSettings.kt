package com.example.oportunia.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.texAndLable
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue


var idSelectedU2 = 0

@Composable
fun StudentInformationSettings2(studentViewModel: StudentViewModel, navController: NavHostController) {
    val studentState by studentViewModel.studentState.collectAsState()
    var showAlert by remember { mutableStateOf(false) }

    // Cargar los datos del estudiante al iniciar la pantalla
    LaunchedEffect(Unit) {
        val userId = studentViewModel.selectedStudent.value?.idUser
            ?: throw IllegalStateException("No hay usuario autenticado")
        studentViewModel.loadStudentByUserId(userId)
    }

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
                        brush = Brush.linearGradient(
                            colors = listOf(
                                royalBlue,
                                deepSkyBlue,
                                midnightBlue
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f)
                        ),
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

            // Mostrar el estado del estudiante
            when (studentState) {
                is StudentState.Loading -> {
                    Text(
                        text = "Cargando...",
                        color = com.example.oportunia.presentation.ui.theme.blackPanter,
                        fontSize = 20.sp
                    )
                }
                is StudentState.Success -> {
                    val student = (studentState as StudentState.Success).student

                    // Inicializar los valores del ViewModel con los datos del estudiante
                    LaunchedEffect(student) {
                        studentViewModel.setNombre(student.name)
                        studentViewModel.setApellido1(student.lastName1)
                        studentViewModel.setApellido2(student.lastName2)
                        studentViewModel.setIdUniversidad(student.universityId ?: 0)
                    }

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

                    // Dropdown de universidad
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 48.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        var selectedUniversity by remember { mutableStateOf("") }

                        // Actualizar el nombre de la universidad seleccionada
                        LaunchedEffect(studentViewModel.universityOptions.collectAsState().value) {
                            val university = studentViewModel.universityOptions.value.find {
                                it.id == student.universityId
                            }
                            selectedUniversity = university?.name ?: ""
                        }

                        UniversityDropdown2(
                            selectedUniversity = selectedUniversity,
                            onUniversitySelected = { selectedUniversity = it },
                            studentViewModel = studentViewModel
                        )
                    }

                    Spacer(modifier = Modifier.height(120.dp))

                    // Botón para guardar cambios
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .padding(horizontal = 60.dp, vertical = 0.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(24.dp),
                                clip = false
                            )
                            .gradientBackgroundBlue(gradientColorsBlue, RoundedCornerShape(10.dp))
                            .clickable {
                                val nombre = studentViewModel.nombre.value.trim()
                                val apellido1 = studentViewModel.apellido1.value.trim()
                                val apellido2 = studentViewModel.apellido2.value.trim()
                                val universidadId = idSelectedU2

                                if (nombre.isNotEmpty() && apellido1.isNotEmpty() && apellido2.isNotEmpty() && universidadId != 0) {

                                    studentViewModel.updateStudent(student.idStudent, student.idUser)

                                    navController.navigate(NavRoutes.Settings.ROUTE)
                                    studentViewModel.clearStudentData()

                                } else {
                                    showAlert = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Guardar Cambios",
                            fontSize = 25.sp,
                            color = com.example.oportunia.presentation.ui.theme.walterWhite,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                is StudentState.Error -> {
                    Text(
                        text = "Error: ${(studentState as StudentState.Error).message}",
                        color = Color.Red,
                        fontSize = 20.sp
                    )
                }
                StudentState.Empty -> {
                    Text(
                        text = "Sin datos de estudiante",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
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
fun UniversityDropdown2(
    selectedUniversity: String,
    onUniversitySelected: (String) -> Unit,
    studentViewModel: StudentViewModel
) {
    LaunchedEffect(Unit) {
        studentViewModel.loadUniversityOptions()
    }

    val options by studentViewModel.universityOptions.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(250.dp)) {
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
                        idSelectedU2 = university.id
                        studentViewModel.setIdUniversidad(university.id)
                        onUniversitySelected(university.name)
                        expanded = false
                    }
                )
            }
        }
    }
}